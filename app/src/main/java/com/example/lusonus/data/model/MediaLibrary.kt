package com.example.lusonus.data.model

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime

open class MediaLibrary {
    // Gets the playlist library.
    private val playlistLibrary = SharedPlaylistLibrary

    // Hot flow that deals with media changing.
    private val _allMedia = MutableStateFlow<List<Media>>(emptyList())

    // Currently displayed version.
    private val _media = MutableStateFlow<List<Media>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val media: StateFlow<List<Media>> = _media.asStateFlow()

    // This adds or updates media, since both were similar it's merged.
    @RequiresApi(Build.VERSION_CODES.O)
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
                updated.add(Media(name = name, dateAdded = LocalDateTime.now() , lastPlayed = null ,uri = uri))
            }
        }

        // Finally, we hot flow the change.
        _allMedia.value = updated
        _media.value = updated
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
    }

    // Sorts the files based on sorting type.
    @RequiresApi(Build.VERSION_CODES.O)
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
