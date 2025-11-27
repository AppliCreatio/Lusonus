package com.example.lusonus.ui.screens.FolderViewScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.appContext
import com.example.lusonus.data.model.Folder
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.SharedFolderLibrary
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.ui.utils.getFileName
import com.example.lusonus.ui.utils.scanFolderRecursive
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FolderLibraryViewModel : ViewModel(), LifecycleEventObserver {

    // Gets the singleton instance of the folder library.
    private val folderLibrary = SharedFolderLibrary

    // Gets the singleton instance of the media library.
    private val mediaLibrary = SharedMediaLibrary

    // Gets the folders from the model.
    val folders: StateFlow<List<Folder>> = folderLibrary.folders

    // This is SUPER important.
    // This makes it so the folders update regardless of where you are in the app when it resumes.
    init {
        // Observe app lifecycle.
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            refreshAllFolders()
        }
    }

    // This methods refreshes all the imported folders (insanely important)
    private fun refreshAllFolders() {
        val context = appContext

        // For each folder we call the refresh.
        folderLibrary.folders.value.forEach { folder ->
            refreshFolder(context, folder.uri)
        }
    }

    // This is the final function needed to inherit from LifecycleEventObserver
    // and is the ending function.
    override fun onCleared() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    // Adds a folder to the storage along with all the media involved with that folder.
    fun addFolder(context: Context, folderUri: Uri, folderName: String, folderMediaUris: List<Uri>) {
        val media = folderMediaUris.map { uri ->
            val name = context.getFileName(uri)
            mediaLibrary.modifyMedia(mapOf(name to uri))
            mediaLibrary.getMedia(name)!!
        }

        // Adds folder to the "storage"
        folderLibrary.addFolder(folderName, folderUri, media)
    }

    // Deletes a folder, deletes all involved media in the library.
    fun deleteFolder(name: String) {
        // Gets the folder to delete, if it can't find it we return.
        folderLibrary.folders.value
            .find { it.name == name }
            ?.let { folderLibrary.removeFolder(it.uri) }
    }

    // Sorts the folders based on provided sorting type.
    fun sortFolders(type: String) {
        // Updates the list.
        folderLibrary.sortFolders(type)
    }

    // Searches the folders based on the query.
    fun searchFolders(query: String) {
        // Updates the list.
        folderLibrary.searchFolders(query)
    }

    // Refreshes the folder (what you call when you update the folder).
    // This is the "parent" function that starts up the coroutine.
    fun refreshFolder(context: Context, folderUri: Uri) {
        // Does a coroutine!!!
        viewModelScope.launch {
            // Does the most important recursive function ever, read it in FileUtils.kt
            val mediaUris = context.scanFolderRecursive(folderUri)

            // Calls the update.
            updateFolder(folderUri, mediaUris, context)
        }
    }

    // Finalizes the updating of a folder.
    private fun updateFolder(folderUri: Uri, newUris: List<Uri>, context: Context) {
        // Gets existing one or returns if it's invalid.
        val existing = folderLibrary.folders.value.find { it.uri == folderUri } ?: return

        // Makes new SnapshotStateList.
        val newMedia = newUris.map { uri ->
            val name = context.getFileName(uri)
            mediaLibrary.modifyMedia(mapOf(name to uri))
            mediaLibrary.getMedia(name)!!
        }

        // Updates the list.
        folderLibrary.replaceFolder(existing.copy(media = newMedia as MutableList<Media>))
    }
}
