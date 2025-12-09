package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.dataclasses.Playlist
import com.example.lusonus.data.sharedinstances.SharedPlaylistLibrary
import kotlinx.coroutines.flow.StateFlow

/*
*   Brandon made this file
*  */

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
