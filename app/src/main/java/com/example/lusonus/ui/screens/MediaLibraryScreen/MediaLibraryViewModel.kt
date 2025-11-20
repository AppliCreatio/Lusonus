package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.ui.utils.getFileName

// Media view model to deal with
class MediaLibraryViewModel : ViewModel() {
    // Gets shared singleton instance.
    private val mediaLibrary = SharedMediaLibrary

    // THIS IS COOL. It only gets the getter for the mediaList, meaning you can't edit it directly.
    // (which is exactly what we want since we want to call the methods,
    // its like having a private set in c#)
    val files = SnapshotStateList<Media>().apply { addAll(mediaLibrary.mediaList) }

    // Add files to files.
    fun addFiles(context: Context, uris: List<Uri>) {
        val pendingMedias = mutableMapOf<String, Uri>()
        uris.forEach { uri ->
            pendingMedias[context.getFileName(uri)] = uri
        }
        mediaLibrary.addMedia(pendingMedias)
        updateFiles()
    }

    // Removes a single media by its URI string
    fun removeFile(uri: Uri) {
        mediaLibrary.removeMedia(uri)
        updateFiles()

    }

    fun refreshMedia(context: Context) {
        mediaLibrary.refreshMedia(context)
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

    private fun updateFiles(newList: List<Media> = mediaLibrary.mediaList) {
        files.clear()
        files.addAll(newList)
    }
}