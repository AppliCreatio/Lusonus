package com.example.lusonus.ui.screens.PlaylistScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.Playlist
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.data.model.SharedPlaylistLibrary

// Media view model to deal with
class PlaylistViewModel(private val playlistName: String) : ViewModel() {
    // Gets shared singleton instance.
    private val playlistLibrary = SharedPlaylistLibrary
    private val mediaLibrary = SharedMediaLibrary

    // Gets the playlist from the name. Does the get(), read next comment for details.
    // This can't be null, but it won't be based on how you get to the screen in the first place.
    private val playlist get() = playlistLibrary.getPlaylist(playlistName)!!

    // THIS IS COOL. It only gets the getter for the playlistList and mediaList,
    // meaning you can't edit it directly.
    // (which is exactly what we want since we want to call the methods,
    // its like having a private set in c#)
    val playlistFiles = mutableStateListOf<Media>().apply { addAll(playlist.media) }
    val mediaFiles get() = mediaLibrary.mediaList

    // Adds selected media to this playlist
    fun addToPlaylist(mediaList: List<Media>) {
        playlistLibrary.addMediaToPlaylist(playlist.name, mediaList)
        updateFiles()
    }

    // Removes a media item from this playlist
    fun removeFromPlaylist(media: Media) {
        playlistLibrary.removeMediaFromPlaylist(playlist.name, media.uri)
        updateFiles()
    }

    fun searchMedia(query: String) {
        val matches = mediaLibrary.searchFiles(query)
        updateFiles(matches)
    }

    // TODO: change the header of input box

    fun sortMedia(type: String) {
        val sorted = mediaLibrary.sortFiles(type)
        updateFiles(sorted)

    }

    private fun updateFiles(newList: List<Media> = playlist.media) {
        playlistFiles.clear()
        playlistFiles.addAll(newList)
    }
}