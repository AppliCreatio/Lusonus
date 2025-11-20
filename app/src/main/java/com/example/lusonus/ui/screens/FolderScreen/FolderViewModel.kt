package com.example.lusonus.ui.screens.FolderScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.SharedFolderLibrary
import com.example.lusonus.data.model.SharedMediaLibrary

class FolderViewModel(private val folderName: String) : ViewModel() {

    private val folderLibrary = SharedFolderLibrary
    private val mediaLibrary = SharedMediaLibrary

    val folder get() = folderLibrary.getFolder(folderName)!!

    val folderFiles = mutableStateListOf<Media>().apply { addAll(folder.media) }

    fun searchMedia(query: String) {
        val matches = mediaLibrary.searchFiles(query)
        updateFiles(matches)
    }

    fun sortMedia(type: String) {
        val sorted = mediaLibrary.sortFiles(type)
        updateFiles(sorted)
    }

    private fun updateFiles(newList: List<Media> = folder.media) {
        folderFiles.clear()
        folderFiles.addAll(newList)
    }
}
