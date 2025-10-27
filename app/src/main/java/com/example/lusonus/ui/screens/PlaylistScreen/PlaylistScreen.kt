package com.example.lusonus.ui.screens.PlaylistScreen

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.PlaylistComposables.MediaPicker
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun PlaylistScreen(
    playlistName: String,
    playlistLibraryViewModel: PlaylistLibraryViewModel,
    mediaLibraryViewModel: MediaLibraryViewModel
) {
    // Gets playlist files. Will update because of snapshot.
    val playlistFiles = playlistLibraryViewModel.getFiles(playlistName)
    val allMediaFiles = mediaLibraryViewModel.files

    var showPicker by rememberSaveable { mutableStateOf(false) }

    MainLayout(
        content = {
            PlaylistContent(
                playlistFiles = playlistFiles,
                onRemoveFromPlaylist = { uri ->
                    playlistLibraryViewModel.removeFileFromPlaylist(playlistName, uriString = uri)
                }
            )

            // This is the picker to add a media to the playlist.
            if (showPicker) {
                AlertDialog(
                    onDismissRequest = { showPicker = false },
                    confirmButton = {},
                    text = {
                        MediaPicker(
                            allMediaFiles = allMediaFiles,
                            playlistFiles = playlistFiles,
                            onAddToPlaylist = { selected ->
                                playlistLibraryViewModel.addFilesToPlaylist(
                                    playlistName = playlistName,
                                    uris = selected
                                )
                                showPicker = false
                            }
                        )
                    }
                )
            }
        },
        screenTitle = playlistName,
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = {
                    showPicker = true
                }
            )
        }
    )
}