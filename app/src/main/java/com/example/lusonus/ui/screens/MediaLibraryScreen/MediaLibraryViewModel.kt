package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.Settings
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.ui.utils.getFileName
import kotlinx.coroutines.flow.StateFlow

// Media view model to deal with
@RequiresApi(Build.VERSION_CODES.O)
class MediaLibraryViewModel(val settings: Settings) : ViewModel() {
    // Gets shared singleton instance.
    private val mediaLibrary = SharedMediaLibrary

    // Used to thing SnapshotStateList was cool... flows are so good.
    val mediaFiles: StateFlow<List<Media>> = mediaLibrary.media

    // Add files to files.

    fun addFiles(context: Context, uris: List<Uri>) {
        // THE RABBIT HOLES! Associate by is really really really cool.
        val map = uris.associateBy { uri -> context.getFileName(uri) }

        // Adds the medias to the data.
        mediaLibrary.modifyMedia(map)
    }

    // Removes a single media by its URI.
    fun removeFile(uri: Uri) {
        mediaLibrary.removeMedia(uri)
    }

    // Refreshes the media list (cleans up dead links).
    fun refreshMedia(context: Context) {
        mediaLibrary.refreshMedia(context)
    }

    // Searches the media by a query.
    fun searchMedia(query: String) {
        mediaLibrary.searchFiles(query)
    }

    // Sorts media by provided sorting type.
    fun sortMedia(type: String) {
        mediaLibrary.sortFiles(type)
    }

    fun filterMedia(restrictionType: Int) {
        mediaLibrary.filterFiles(restrictionType)
    }
}