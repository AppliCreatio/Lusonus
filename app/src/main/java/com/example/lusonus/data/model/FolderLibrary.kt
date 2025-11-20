package com.example.lusonus.data.model

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort

open class FolderLibrary {

    val folders: SnapshotStateList<Folder> = mutableStateListOf()

    // Create folder if it doesn't exist
    fun addFolder(name: String, uri: Uri, mediaInFolder: List<Media>) {
        if (folders.none { it.uri == uri }) {
            folders.add(
                Folder(
                    name = name,
                    uri = uri,
                    media = mutableStateListOf<Media>().apply { addAll(mediaInFolder) }
                )
            )
        }
    }

    // Updates the folder.
    fun replaceFolder(newFolder: Folder) {
        val index = folders.indexOfFirst { it.uri == newFolder.uri }
        if (index != -1) {
            // Replace with a new snapshot folder.
            folders[index] = newFolder
        }
    }

    // Remove folder
    fun removeFolder(uri: Uri) {
        val folder = folders.find { it.uri == uri } ?: return

        folder.media.forEach { media ->
            SharedMediaLibrary.removeMedia(media.uri)
        }

        folders.remove(folder)
    }


    fun getFolder(name: String): Folder? {
        return folders.find { it.name == name }
    }

    fun clear() {
        folders.clear()
    }

    fun sortFolders(type: String) = sort(folders, type)

    fun searchFolders(query: String) =
        if (query.isNotBlank()) search(folders, query)
        else folders
}