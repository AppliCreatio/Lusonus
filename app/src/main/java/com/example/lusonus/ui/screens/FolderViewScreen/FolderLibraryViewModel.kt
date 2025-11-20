package com.example.lusonus.ui.screens.folders

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.documentfile.provider.DocumentFile
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
        updateFolderDisplay()
    }

    fun deleteFolder(name: String) {
        val folder = displayedFolders.find { it.name == name } ?: return
        folderLibrary.removeFolder(folder.uri)
        updateFolderDisplay()
    }


    fun sortFolders(type: String) {
        val sorted = folderLibrary.sortFolders(type)
        updateFolderDisplay(sorted)
    }

    fun searchFolders(query: String) {
        val matches = folderLibrary.searchFolders(query)
        updateFolderDisplay(matches)
    }

    fun refreshFolder(context: Context, folderUri: Uri) {
        val mediaUris = scanFolder(context, folderUri)
        updateFolder(folderUri, mediaUris, context)
    }

    // Checks to see if their are invalid uris in the folder
    private fun scanFolder(context: Context, folderUri: Uri): List<Uri> {
        // gets the contents of the folder
        val docFile = DocumentFile.fromTreeUri(context, folderUri) ?: return emptyList()

        // returns the new cleaned version of the folder
        return docFile.listFiles().filter { it.isFile }.mapNotNull { it.uri }
    }

    private fun updateFolder(folderUri: Uri, newMediaUris: List<Uri>, context: Context) {
        // Gets existing one or returns if it's invalid.
        val existing = folderLibrary.folders.find { it.uri == folderUri } ?: return

        // Makes new SnapshotStateList.
        val updatedMedia = SnapshotStateList<Media>().apply {
            newMediaUris.forEach { uri ->
                val name = context.getFileName(uri)
                mediaLibrary.addMedia(mapOf(name to uri))
                mediaLibrary.getMedia(name)?.let { add(it) }
            }
        }

        // Create a new folder list with the new media list.
        val updatedFolder = existing.copy(media = updatedMedia)

        // Replaces folder in shared library.
        folderLibrary.replaceFolder(updatedFolder)

        // Updates UI snapshot.
        updateFolderDisplay()
    }

    // Updates the folder list presented to the user.
    private fun updateFolderDisplay(newList: List<Folder> = folderLibrary.folders) {
        displayedFolders.clear()
        displayedFolders.addAll(newList)
    }
}
