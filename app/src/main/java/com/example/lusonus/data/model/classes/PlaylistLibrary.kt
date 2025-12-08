package com.example.lusonus.data.model.classes

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.core.net.toUri
import com.example.lusonus.appContext
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.data.dataclasses.Playlist
import com.example.lusonus.data.dataclasses.proto.MediaProto
import com.example.lusonus.data.dataclasses.proto.PlaylistProto
import com.example.lusonus.data.dataclasses.protodatastore.mediaLibraryDataStore
import com.example.lusonus.data.dataclasses.protodatastore.playlistLibraryDataStore
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class PlaylistLibrary {
    // Application context.
    private val context: Context = appContext

    // Hot flow that deals with media changing.
    private val _allPlaylists = MutableStateFlow<List<Playlist>>(emptyList())

    // Currently displayed version.
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val playlists: StateFlow<List<Playlist>> = _playlists

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
            val stored = context.playlistLibraryDataStore.data.first()
            val restored = stored.playlistsList.map { it.toPlaylist() }

            _allPlaylists.value = restored
            _playlists.value = restored

            _hasRestoredData.value = true
        }
    }

    // Convert playlist dataclass to the generated proto type.
    private fun Playlist.toProto(): PlaylistProto =
        PlaylistProto.newBuilder()
            .setName(name)
            .setDateAdded(dateAdded.format(formatter))
            .setLastPlayed(lastPlayed?.format(formatter) ?: "")
            .setImage(image.toString())
            .addAllMediaItems(media.map { it.toProto() })
            .build()

    // Convert proto to playlist dataclass.
    private fun PlaylistProto.toPlaylist(): Playlist {
        val parsedDateAdded = runCatching { LocalDateTime.parse(dateAdded, formatter) }
            .getOrElse { LocalDateTime.now() }
        val parsedLastPlayed = runCatching {
            if (lastPlayed.isBlank()) null else LocalDateTime.parse(lastPlayed, formatter)
        }.getOrNull()

        return Playlist(
            name = name,
            dateAdded = parsedDateAdded,
            lastPlayed = parsedLastPlayed,
            image = image.toUri(),
            media = mutableStateListOf<Media>().apply {
                addAll(mediaItemsList.map { it.toMedia() })
            }
        )
    }

    private fun Media.toProto(): MediaProto =
        MediaProto.newBuilder()
            .setUri(uri.toString())
            .setName(name)
            .setDateAdded(dateAdded.format(formatter))
            .setLastPlayed(lastPlayed?.format(formatter) ?: "")
            .build()

    private fun MediaProto.toMedia(): Media =
        Media(
            name = name,
            dateAdded = runCatching { LocalDateTime.parse(dateAdded, formatter) }.getOrElse { LocalDateTime.now() },
            lastPlayed = runCatching {
                if (lastPlayed.isBlank()) null else LocalDateTime.parse(lastPlayed, formatter)
            }.getOrNull(),
            uri = uri.toUri()
        )

    fun persist() {
        scope.launch {
            if (!_hasRestoredData.value) return@launch

            val current = context.playlistLibraryDataStore.data.first()
            val newProtos = _allPlaylists.value.map { it.toProto() }

            if (newProtos == current.playlistsList) return@launch

            context.playlistLibraryDataStore.updateData {
                it.toBuilder().clearPlaylists().addAllPlaylists(newProtos).build()
            }
        }
    }

    // Makes a playlist instance and adds it to the list.
    fun createPlaylist(name: String) {
        if (_allPlaylists.value.none { it.name == name }) {
            val newPlaylist =
                Playlist(name = name, dateAdded = LocalDateTime.now(), image = null, lastPlayed = null)
            _allPlaylists.value += newPlaylist
            _playlists.value += newPlaylist
            persist()
        }
    }

    // Deletes a playlist by it's name.
    fun deletePlaylist(name: String) {
        _allPlaylists.value = _allPlaylists.value.filterNot { it.name == name }
        _playlists.value = _allPlaylists.value
        persist()
    }

    // Adds specific media to a playlist.
    fun addMediaToPlaylist(
        playlistName: String,
        mediaList: List<Media>,
    ) {
        _allPlaylists.value =
            _allPlaylists.value.map { playlist ->
                if (playlist.name == playlistName) {
                    val newMedia =
                        (playlist.media + mediaList.filter { media -> playlist.media.none { it.uri == media.uri } })
                    playlist.copy(media = newMedia.toMutableStateList())
                } else {
                    playlist
                }
            }

        _playlists.value = _allPlaylists.value
        persist()
    }

    // Removes a specific media from a playlist.
    fun removeMediaFromPlaylist(
        playlistName: String,
        mediaUri: Uri,
    ) {
        _allPlaylists.value =
            _allPlaylists.value.map { playlist ->
                if (playlist.name == playlistName) {
                    val newMedia = playlist.media.filter { it.uri != mediaUri }.toMutableStateList()
                    playlist.copy(media = newMedia)
                } else {
                    playlist
                }
            }

        _playlists.value = _allPlaylists.value
        persist()
    }

    // Removes a media from all playlists (is needed for when a media gets deleted)
    fun removeMediaFromAllPlaylists(uri: Uri) {
        _allPlaylists.value =
            _allPlaylists.value.map { playlist ->
                val newMedia = playlist.media.filter { it.uri != uri }.toMutableStateList()
                playlist.copy(media = newMedia)
            }

        _playlists.value = _allPlaylists.value
        persist()
    }

    // Gets a specific playlist.
    fun getPlaylist(name: String): Playlist? = _allPlaylists.value.find { it.name == name }

    // Sorts all playlists
    fun sortPlaylists(type: String) {
        _playlists.value = sort(_playlists.value, type)
    }

    // Searches through the playlists
    fun searchPlaylists(query: String) {
        _playlists.value =
            if (query.isBlank()) {
                _allPlaylists.value
            } else {
                search(_allPlaylists.value, query)
            }
    }

    fun updatePlaylist(oldName: String, newName: String, newImage: Uri?) {
        _allPlaylists.value = _allPlaylists.value.map { playlist ->
            if (playlist.name == oldName) {
                playlist.copy(name = newName, image = newImage)
            } else {
                playlist
            }
        }

        _playlists.value = _allPlaylists.value
        persist()
    }
}
