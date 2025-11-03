package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sortMedia

// Media view model to deal with
class MediaLibraryViewModel : ViewModel() {
    // Gets shared singleton instance.
    private val mediaLibrary = SharedMediaLibrary

    // THIS IS COOL. It only gets the getter for the mediaList, meaning you can't edit it directly.
    // (which is exactly what we want since we want to call the methods,
    // its like having a private set in c#)
    val files get() = mediaLibrary.mediaList

    // Add files to files.
    fun addFiles(uris: List<Uri>) {
        mediaLibrary.addMedia(uris)
    }

    // Removes a single media by its URI string
    fun removeFile(uri: Uri) {
        mediaLibrary.removeMedia(uri)
    }

//    fun sortFiles(type: String, context: Context){
//        val temp = sortMedia(filesShown, context, type)
//        filesShown.clear()
//        filesShown.addAll(temp)
//    }
//
//    fun searchMedia(context: Context, query: String){
//        if(query.isNotBlank()){
//            val matches = search(filesShown, query, context)
//            filesShown.clear()
//            filesShown.addAll(matches)
//        }
//        else {
//            filesShown.clear()
//            filesShown.addAll(files)
//        }
//    }
}