package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.SharedPlaylistLibrary
import com.example.lusonus.data.model.Media

class PlaylistLibraryViewModel : ViewModel() {

    private val playlistLibrary = SharedPlaylistLibrary

    // Creates a playlist.
    fun createPlaylist(name: String) {
        playlistLibrary.createPlaylist(name)
    }

    // Deletes a playlist.
    fun deletePlaylist(name: String) {
        playlistLibrary.deletePlaylist(name)
    }

    // Get all playlist names.
    fun getAllPlaylists(): List<String> {
        return playlistLibrary.getAllPlaylists().map { it.name }
    }
}
