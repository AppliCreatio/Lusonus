package com.example.lusonus.data.model.classes

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.example.lusonus.appContext
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.data.dataclasses.proto.MediaProto
import com.example.lusonus.data.dataclasses.protodatastore.mediaLibraryDataStore
import com.example.lusonus.data.sharedinstances.SharedPlaylistLibrary
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
*   Brandon made this entire file
*  */

open class MediaLibrary {
    // Application context.
    private val context: Context = appContext

    // Gets the playlist library.
    private val playlistLibrary = SharedPlaylistLibrary

    // Hot flow that deals with media changing.
    private val _allMedia = MutableStateFlow<List<Media>>(emptyList())

    // Currently displayed version.
    private val _media = MutableStateFlow<List<Media>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val media: StateFlow<List<Media>> = _media.asStateFlow()

    // Background scope for IO operations.
    private val scope = CoroutineScope(Dispatchers.IO)

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // Prevent accidental overwrites before initial load finishes.
    private val _hasRestoredData = MutableStateFlow(false)

    init {
        restoreFromDataStore()
    }

    private fun restoreFromDataStore() {
        scope.launch {
            try {
                val stored = context.mediaLibraryDataStore.data.first()
                val restoredList = stored.itemsList.map { it.toMedia() }

                _allMedia.value = restoredList
                _media.value = restoredList
            } catch (e: Exception) {
                _allMedia.value = emptyList()
                _media.value = emptyList()
            } finally {
                _hasRestoredData.value = true
            }
        }
    }

    // Convert Media dataclass to the generated proto type.
    private fun Media.toProto(): MediaProto =
        MediaProto.newBuilder()
            .setUri(uri.toString())
            .setName(name)
            .setDateAdded(dateAdded.format(formatter))
            .setLastPlayed(lastPlayed?.format(formatter) ?: "")
            .build()

    // Convert proto to Media dataclass.
    private fun MediaProto.toMedia(): Media {
        val parsedDateAdded = runCatching {
            LocalDateTime.parse(
                dateAdded,
                formatter
            )
        }.getOrElse { LocalDateTime.now() }
        val parsedLastPlayed = runCatching {
            if (lastPlayed.isBlank()) null else LocalDateTime.parse(lastPlayed, formatter)
        }.getOrNull()

        return Media(
            name = name,
            dateAdded = parsedDateAdded,
            lastPlayed = parsedLastPlayed,
            uri = uri.toUri()
        )
    }

    fun persist(force: Boolean = false) {
        scope.launch {
            if (!_hasRestoredData.value && !force) {
                return@launch
            }

            val items = _allMedia.value
            val current = context.mediaLibraryDataStore.data.first()

            val newProtos = items.map { it.toProto() }

            if (!force &&
                newProtos.size == current.itemsCount &&
                newProtos.zip(current.itemsList).all { (a, b) -> a == b }
            ) {
                return@launch
            }

            if (!force && items.isEmpty() && current.itemsCount > 0) {
                return@launch
            }

            context.mediaLibraryDataStore.updateData { old ->
                old.toBuilder().clearItems().addAllItems(newProtos).build()
            }
        }
    }

    // This adds or updates media, since both were similar it's merged.
    fun modifyMedia(pendingMedia: Map<String, Uri>) {
        val updated = _allMedia.value.toMutableList()

        pendingMedia.forEach { (name, uri) ->

            // Checks to see if already exists.
            val existing = updated.find { it.uri == uri }

            // If it does we update the name.
            if (existing != null) {
                // Checks to make sure the name actually changed xD
                if (existing.name != name) {
                    // Get the spot the current one is at, and swap it with the copy with a new name.
                    updated[updated.indexOf(existing)] = existing.copy(name = name)
                }
            } else {
                // If it doesn't exist we add it.
                updated.add(
                    Media(
                        name = name,
                        dateAdded = LocalDateTime.now(),
                        lastPlayed = null,
                        uri = uri
                    )
                )
            }
        }

        // Finally, we hot flow the change.
        _allMedia.value = updated
        _media.value = updated

        // Persist the change.
        persist()
    }

    // Removes media from the flow.
    fun removeMedia(uri: Uri) {
        // The current flow.
        val before = _allMedia.value

        // After we filter out the removal.
        val after = before.filter { it.uri != uri }

        // If we removed something...
        // We have to be careful to watch the number of recompositions we are doing.
        if (after.size != before.size) {
            // Updates the flow.
            _allMedia.value = after
            _media.value = after

            // Makes sure to update relevant playlists.
            playlistLibrary.removeMediaFromAllPlaylists(uri)

            // Persist the change.
            persist()
        }
    }

    // Gets a specific media.
    fun getMedia(name: String): Media? = _allMedia.value.find { it.name == name }

    // Updates the media list, this is VERY different from updating a media.
    // This is the janitor rather than the engineer.
    fun refreshMedia(context: Context) {
        // Filters out the invalid.
        val refreshed = _allMedia.value.filter { media ->
            DocumentFile.fromSingleUri(context, media.uri)?.exists() == true
        }

        // Updates the flow.
        _allMedia.value = refreshed
        _media.value = refreshed

        // Persist the refresh.
        persist()
    }

    // Sorts the files based on sorting type.
    fun sortFiles(type: String) {
        // Updates the flow.
        _media.value = sort(_media.value, type)
    }

    // Searches files by query.
    fun searchFiles(query: String) {
        _media.value = if (query.isBlank()) {
            _allMedia.value
        } else {
            search(_allMedia.value, query)
        }
    }
}