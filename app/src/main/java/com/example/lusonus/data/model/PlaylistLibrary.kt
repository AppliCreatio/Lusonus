package com.example.lusonus.data.model

import android.net.Uri
import androidx.compose.runtime.toMutableStateList
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class PlaylistLibrary {

    // Hot flow that deals with media changing.
    private val _allPlaylists = MutableStateFlow<List<Playlist>>(emptyList())

    // Currently displayed version.
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val playlists: StateFlow<List<Playlist>> = _playlists


    // Makes a playlist instance and adds it to the list.
    fun createPlaylist(name: String) {
        if (_allPlaylists.value.none { it.name == name }) {
            val newPlaylist = Playlist(name = name)
            _allPlaylists.value = _allPlaylists.value + newPlaylist
            _playlists.value = _playlists.value + newPlaylist
        }
    }

    // Deletes a playlist by it's name.
    fun deletePlaylist(name: String) {
        _allPlaylists.value = _allPlaylists.value.filterNot { it.name == name }
        _playlists.value = _allPlaylists.value
    }

    // Adds specific media to a playlist.
    fun addMediaToPlaylist(playlistName: String, mediaList: List<Media>) {
        _allPlaylists.value = _allPlaylists.value.map { playlist ->
            if (playlist.name == playlistName) {
                val newMedia = (playlist.media + mediaList.filter { media -> playlist.media.none { it.uri == media.uri } })
                playlist.copy(media = newMedia.toMutableStateList())
            } else playlist
        }

        _playlists.value = _allPlaylists.value
    }

    // Removes a specific media from a playlist.
    fun removeMediaFromPlaylist(playlistName: String, mediaUri: Uri) {
        _allPlaylists.value = _allPlaylists.value.map { playlist ->
            if (playlist.name == playlistName) {
                val newMedia = playlist.media.filter { it.uri != mediaUri }.toMutableStateList()
                playlist.copy(media = newMedia)
            } else playlist
        }

        _playlists.value = _allPlaylists.value
    }

    // Removes a media from all playlists (is needed for when a media gets deleted)
    fun removeMediaFromAllPlaylists(uri: Uri) {
        _allPlaylists.value = _allPlaylists.value.map { playlist ->
            val newMedia = playlist.media.filter { it.uri != uri }.toMutableStateList()
            playlist.copy(media = newMedia)
        }

        _playlists.value = _allPlaylists.value
    }

    // Gets a specific playlist.
    fun getPlaylist(name: String): Playlist? =
        _allPlaylists.value.find { it.name == name }

    // Sorts all playlists
    fun sortPlaylists(type: String) {
        _playlists.value = sort(_playlists.value, type)
    }

    //Searches through the playlists
    fun searchPlaylists(query: String) {
        _playlists.value = if (query.isBlank()) {
            _allPlaylists.value
        } else {
            search(_allPlaylists.value, query)
        }
    }
}
