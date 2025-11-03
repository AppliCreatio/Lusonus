package com.example.lusonus.data.model

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.Date

open class MediaLibrary {
    // Gets the playlist library.
    private val playlistLibrary = SharedPlaylistLibrary

    // This holds all the media in memory.
    // Made it observable so all screens can react to changes of it.
    val mediaList: SnapshotStateList<Media> = mutableStateListOf()

    // Method to addMedia to the Library, we initialize media items.
    fun addMedia(uris: List<Uri>) {
        val now = Date()
        uris.forEach { uri ->
            // Makes sure to not have duplicates.
            if (mediaList.none { it.uri == uri }) {
                val name = uri.lastPathSegment ?: "Unknown"
                mediaList.add(Media(name, uri))
            }
        }
    }

    // Method to remove media from the list.
    fun removeMedia(uri: Uri) {
        // Attempts to remove from MediaLibrary
        val wasRemoved = mediaList.removeAll { it.uri == uri }

        // If it was removed, removes from playlists.
        if (wasRemoved) {
            playlistLibrary.removeMediaFromAllPlaylists(uri)
        }
    }

    // Gets a specific media.
    fun getMedia(name: String): Media? =
        mediaList.find { it.name == name }

    // Method that clears the MediaLibrary.
    fun clear() {
        mediaList.clear()
    }
}
