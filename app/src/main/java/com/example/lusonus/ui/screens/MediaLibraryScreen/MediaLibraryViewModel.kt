package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sortMedia

// Media view model to deal with
class MediaLibraryViewModel : ViewModel() {
    // This is a list of "pointers" (Uri) to files.
    // We will use this to get file information to display/use.
    // This was super annoying, basically since mutableStateListOf is not supported by
    // rememberSaveable, I had to use a Saver.
    val files = mutableStateListOf<String>()
    val filesShown = mutableStateListOf<String>().apply { addAll(files) }

    // Add files to files.
    fun addFiles(uris: List<Uri>) {
        files.addAll(elements = uris.map { it.toString() })
        filesShown.addAll(elements = uris.map { it.toString() })
    }

    // Remove a single song by its URI string
    fun removeFile(uriString: String, playlistLibraryViewModel: PlaylistLibraryViewModel? = null) {
        files.remove(element = uriString)
        filesShown.remove(element = uriString)

        // Make's sure to remove it from playlists
        playlistLibraryViewModel?.let {
            playlistViewModel ->
            playlistViewModel.playlists.forEach {
                // Each key value pair, (every playlist's files)
                (playlistName, playlistFiles) ->
                playlistFiles.remove(element = uriString)
            }
        }
    }

    fun sortFiles(type: String, context: Context){
        val temp = sortMedia(filesShown, context, type)
        filesShown.clear()
        filesShown.addAll(temp)
    }

    fun searchMedia(context: Context, query: String){
        if(query.isNotBlank()){
            val matches = search(filesShown, query, context)
            filesShown.clear()
            filesShown.addAll(matches)
        }
        else {
            filesShown.clear()
            filesShown.addAll(files)
        }
    }
}