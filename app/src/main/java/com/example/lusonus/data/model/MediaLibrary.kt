package com.example.lusonus.data.model

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.documentfile.provider.DocumentFile
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import java.util.Date

open class MediaLibrary {
    // Gets the playlist library.
    private val playlistLibrary = SharedPlaylistLibrary

    // This holds all the media in memory.
    // Made it observable so all screens can react to changes of it.
    val mediaList: SnapshotStateList<Media> = mutableStateListOf()

    // Method to addMedia to the Library, we initialize media items.
    fun addMedia(pendingMedia: Map<String, Uri>) {
        pendingMedia.forEach { (key, value) ->
            // Makes sure to not have duplicates.
            if (mediaList.none { it.uri == value && it.name == key }) {
                mediaList.add(Media(key, value))
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
    fun getMedia(name: String): Media? {
        return mediaList.find { it.name == name }
    }

    fun refreshMedia(context: Context) {
        // Builds a new SnapshotStateList
        val refreshed = mutableListOf<Media>()

        mediaList.forEach { media ->
            val doc = DocumentFile.fromSingleUri(context, media.uri)

            // Only keeps if the file still exists.
            if (doc != null && doc.exists()) {
                refreshed.add(media)
            }
        }

        mediaList.clear()
        mediaList.addAll(refreshed)
    }

    // Sorts the files
    fun sortFiles(type: String) = sort(mediaList, type)


    // Searches through the files
    fun searchFiles(query: String): List<Media> {
        return if (query.isNotBlank()){
            search(mediaList, query)
        } else {
            mediaList
        }
    }
}
