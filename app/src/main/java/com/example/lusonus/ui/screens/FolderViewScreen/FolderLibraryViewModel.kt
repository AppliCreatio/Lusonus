package com.example.lusonus.ui.screens.folders

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.*
import com.example.lusonus.ui.utils.getFileName

class FolderLibraryViewModel : ViewModel() {

    private val folderLibrary = SharedFolderLibrary
    private val mediaLibrary = SharedMediaLibrary

    // Local copy that UI observes — same as PlaylistLibraryViewModel
    val displayedFolders = mutableStateListOf<Folder>().apply {
        addAll(folderLibrary.folders)
    }

    // UI will expect this → same pattern as playlists
    fun getAllFolders(): List<String> {
        return displayedFolders.map { it.name }
    }

    fun addFolder(
        context: Context,
        folderUri: Uri,
        folderName: String,
        folderMediaUris: List<Uri>
    ) {
        val media = folderMediaUris.map { uri ->
            val name = context.getFileName(uri)
            mediaLibrary.addMedia(mapOf(name to uri))
            mediaLibrary.getMedia(name)!!
        }

        folderLibrary.addFolder(folderName, folderUri, media)
        updateFolders()
    }

    fun deleteFolder(name: String) {
        val folder = displayedFolders.find { it.name == name } ?: return
        folderLibrary.removeFolder(folder.uri)
        updateFolders()
    }


    fun sortFolders(type: String) {
        val sorted = folderLibrary.sortFolders(type)
        updateFolders(sorted)
    }

    fun searchFolders(query: String) {
        val matches = folderLibrary.searchFolders(query)
        updateFolders(matches)
    }

    private fun updateFolders(newList: List<Folder> = folderLibrary.folders) {
        displayedFolders.clear()
        displayedFolders.addAll(newList)
    }
}
