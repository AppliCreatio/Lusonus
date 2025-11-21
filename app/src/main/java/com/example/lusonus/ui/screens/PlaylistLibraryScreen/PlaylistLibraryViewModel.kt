package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.SharedPlaylistLibrary
import androidx.compose.runtime.mutableStateListOf
import com.example.lusonus.data.model.Playlist
import kotlinx.coroutines.flow.StateFlow

class PlaylistLibraryViewModel : ViewModel() {

    private val playlistLibrary = SharedPlaylistLibrary
    val playlists: StateFlow<List<Playlist>> = playlistLibrary.playlists

    // Creates a playlist.
    fun createPlaylist(name: String) {
        playlistLibrary.createPlaylist(name)
    }

    // Deletes a playlist.
    fun deletePlaylist(name: String) {
        playlistLibrary.deletePlaylist(name)
    }

    fun sortPlaylist(type: String) {
        playlistLibrary.sortPlaylists(type)
    }

    fun searchPlaylists(query: String) {
        playlistLibrary.searchPlaylists(query)
    }
}
