package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class PlaylistLibraryViewModel : ViewModel() {

    // Creates a dictionary to hold the playlists (which can survive).
    // Found out how to make it a private set since we shouldn't modify it outside directly.
    var playlists = mutableStateMapOf<String, SnapshotStateList<String>>()
        private set

    // Creates a playlist and adds it to playlists dictionary.
    fun createPlaylist(name: String) {
        // If playlist doesn't already exist, add it.
        if (name !in playlists) {
            playlists[name] = mutableStateListOf()
        }
    }

    // Adds files to a playlist,
    fun addFilesToPlaylist(playlistName: String, uris: List<String>) {
        // Gets current files already there, or returns if the playlist is null.
        val playlistFiles = playlists[playlistName] ?: return

        // Adds the new files to the current files.
        playlistFiles.addAll(elements = uris)

        // Readds the files back to the dictionary.
        playlists[playlistName] = playlistFiles
    }

    // Removes files from a playlist.
    fun removeFileFromPlaylist(playlistName: String, uriString: String) {
        // Removes files from playlist if possible.
        playlists[playlistName]?.remove(element = uriString)
    }

    // Deletes a playlist.
    fun deletePlaylist(name: String) {
        // Removes it from the dictionary.
        playlists.remove(key = name)
    }

    // Gets the files from a playlist.
    fun getFiles(playlistName: String): List<String> {
        // GetOrPut is so goofy. It makes sure it's not null when fetched.
        return playlists.getOrPut(key = playlistName) { mutableStateListOf() }
    }

    // Gets every created playlist.
    fun getAllPlaylists(): List<String> {
        // We just want the playlist names.
        return playlists.keys.toList()
    }
}