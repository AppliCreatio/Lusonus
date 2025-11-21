package com.example.lusonus.ui.screens.FolderScreen

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.SharedFolderLibrary
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.ui.utils.getFileName
import com.example.lusonus.ui.utils.scanFolderRecursive
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FolderViewModel(private val folderName: String) : ViewModel() {

    // Gets folder library model.
    private val folderLibrary = SharedFolderLibrary

    // Hot flow holding just this folder's files.
    private val _folderFiles = MutableStateFlow<List<Media>>(emptyList())

    // READONLY version for UI.
    val folderFiles: StateFlow<List<Media>> = _folderFiles.asStateFlow()

    // This is called when view model is made, is helpful since we need to get the folder safely.
    init {
        // Performs coroutine.
        viewModelScope.launch {
            // Collect's current in hot flow so it's easy to get folder name.
            folderLibrary.folders.collect {
                folders ->

                // Get the folder this view model belongs to.
                val folder = folders.find { it.name == folderName }

                // If the folder is not null.
                if (folder != null) {
                    // Update local list to match new folder state.
                    _folderFiles.value = folder.media
                }
            }
        }
    }

    // Refreshes the media list (cleans up dead links/find new links).
    fun refreshMedia(context: Context) {
        // Launch the coroutine.
        viewModelScope.launch(Dispatchers.IO) {
            // Get the folder we are in.
            val folder = folderLibrary.getFolder(folderName) ?: return@launch // again this is an Android studio recommended return.

            val scannedUris = context.scanFolderRecursive(folder.uri)

            // Maps new Uris to medias.
            val scannedMedia = scannedUris.mapNotNull { uri ->
                val name = context.getFileName(uri)
                SharedMediaLibrary.modifyMedia(mapOf(name to uri))
                SharedMediaLibrary.getMedia(name)
            }

            // Updates the flow.
            _folderFiles.value = scannedMedia

            // Updates the original folder.
            folderLibrary.replaceFolder(
                folder.copy(
                    media = scannedMedia.toMutableList()
                )
            )
        }
    }

    // Sort based on sorting type.
    fun sortMedia(type: String) {
        val base = folderLibrary.getFolder(folderName)?.media ?: return
        val sorted = sort(base, type)
        _folderFiles.value = sorted
    }

    // Search based on query.
    fun searchMedia(query: String) {
        val base = folderLibrary.getFolder(folderName)?.media ?: return

        _folderFiles.value =
            if (query.isBlank()) base
            else search(base, query)
    }

}
