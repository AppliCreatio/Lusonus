package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

// Media view model to deal with
class MediaLibraryViewModel : ViewModel() {
    // This is a list of "pointers" (Uri) to files.
    // We will use this to get file information to display/use.
    // This was super annoying, basically since mutableStateListOf is not supported by
    // rememberSaveable, I had to use a Saver.
    val files = mutableStateListOf<String>()

    // Add files to files.
    fun addFiles(uris: List<Uri>) {
        files.addAll(elements = uris.map { it.toString() })
    }

    // Remove a single song by its URI string
    fun removeFile(uriString: String, playlistLibraryViewModel: PlaylistLibraryViewModel? = null) {
        files.remove(element = uriString)

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
}