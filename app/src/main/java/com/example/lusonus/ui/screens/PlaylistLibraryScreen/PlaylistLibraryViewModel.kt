package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.SharedPlaylistLibrary
import androidx.compose.runtime.mutableStateListOf
import com.example.lusonus.data.model.Playlist

class PlaylistLibraryViewModel : ViewModel() {

    private val playlistLibrary = SharedPlaylistLibrary
    val displayedPlaylists = mutableStateListOf<Playlist>().apply { addAll(playlistLibrary.playlists) }

    // Creates a playlist.
    fun createPlaylist(name: String) {
        playlistLibrary.createPlaylist(name)
        updatePlaylists()
    }

    // Deletes a playlist.
    fun deletePlaylist(name: String) {
        playlistLibrary.deletePlaylist(name)
        updatePlaylists()
    }

    // Get all playlist names.
    fun getAllPlaylists(): List<String> {
        return displayedPlaylists.map { it.name }
    }

    fun sortPlaylist(type: String) {
        val sortedPlaylist = playlistLibrary.sortPlaylists(type)
        updatePlaylists(sortedPlaylist)
    }

    fun searchPlaylists(query: String) {
        val matches = playlistLibrary.searchPlaylists(query)
        updatePlaylists(matches)
    }

    fun updatePlaylists(newList: List<Playlist> = playlistLibrary.playlists){
            displayedPlaylists.clear()
            displayedPlaylists.addAll(newList)
    }
}
