package com.example.lusonus.data.model

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort

open class PlaylistLibrary {

    // Implements the observer's pattern. We made it a list to make it easier to sort/filter.
    val playlists: SnapshotStateList<Playlist> = mutableStateListOf()

    // Makes a playlist instance and adds it to the list.
    fun createPlaylist(name: String) {
        if (playlists.none { it.name == name }) {
            playlists.add(
                Playlist(
                    name = name,
                    media = mutableStateListOf(),
                    //dateAdded = Date()
                )
            )
        }
    }

    // Deletes a playlist by it's name.
    fun deletePlaylist(name: String) {
        playlists.removeAll { it.name == name }
    }

    // Adds specific media to a playlist.
    fun addMediaToPlaylist(playlistName: String, mediaList: List<Media>) {
        playlists.find { it.name == playlistName }?.let { playlist ->
            mediaList.forEach { media ->
                // Makes sure to not have duplicates of the same media.
                if (playlist.media.none { it.uri == media.uri }) {
                    playlist.media.add(media)
                }
            }
        }
    }

    // Removes a specific media from a playlist.
    fun removeMediaFromPlaylist(playlistName: String, mediaUri: Uri) {
        playlists.find { it.name == playlistName }?.media?.removeAll {
            it.uri == mediaUri
        }
    }

    // Removes a media from all playlists (is needed for when a media gets deleted)
    fun removeMediaFromAllPlaylists(uri: Uri) {
        playlists.forEach { playlist ->
            playlist.media.removeAll { it.uri == uri }
        }
    }

    // Get's a specific playlist.
    fun getPlaylist(name: String): Playlist? =
        playlists.find { it.name == name }

    // Get's all playlists.
    fun getAllPlaylists(): List<Playlist> = playlists

    // Sorts all playlists
    fun sortPlaylists(type: String) = sort(playlists, type)

    //Searches through the playlists
    fun searchPlaylists(query: String) = search(playlists, query)

}
