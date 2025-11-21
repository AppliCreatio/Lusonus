package com.example.lusonus.data.model

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class FolderLibrary {

    // Hot flow that deals with folder changing.
    private val _allFolders = MutableStateFlow<List<Folder>>(emptyList())

    // Currently displayed version.
    private val _folders = MutableStateFlow<List<Folder>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val folders: StateFlow<List<Folder>> = _folders.asStateFlow()

    // Adds a folder if it doesn't already exist.
    fun addFolder(name: String, uri: Uri, mediaInFolder: List<Media>) {
        // Gets the current flow state.
        val current = _allFolders.value

        // Checks to see if folder is already imported, if it is we return.
        if (current.any { it.uri == uri }) return

        // If the folder doesn't exist, we make a new one.
        val newFolder = Folder(
            name = name,
            uri = uri,
            media = mediaInFolder.toMutableList()
        )

        // Updates the flow.
        _allFolders.value = current + newFolder
        _folders.value = current + newFolder
    }

    // Updates the folder.
    fun replaceFolder(newFolder: Folder) {
        // Updates the specific folder in the flow by using URIs.
        _allFolders.value = _allFolders.value.map {
            if (it.uri == newFolder.uri) newFolder else it
        }

        _folders.value = _allFolders.value
    }

    // Removes the folder by URI.
    fun removeFolder(uri: Uri) {
        // Gets the folder to remove or returns if it doesn't exists.
        val folder = _allFolders.value.find { it.uri == uri } ?: return

        // Removes media imported from this folder.
        folder.media.forEach { media ->
            SharedMediaLibrary.removeMedia(media.uri)
        }

        // Removes the folder.
        _allFolders.value = _allFolders.value.filter { it.uri != uri }
        _folders.value = _folders.value.filter { it.uri != uri }
    }

    // Gets a Folder by it's name.
    fun getFolder(name: String): Folder? =
        _allFolders.value.find { it.name == name }

    // Sorts folders by a sorting type.
    fun sortFolders(type: String) {
        _folders.value = sort(_folders.value, type)
    }

    // Searches for folders based on a query.
    fun searchFolders(query: String) {
        _folders.value = if (query.isBlank()) {
            _allFolders.value
        } else {
            search(_allFolders.value, query)
        }
    }
}