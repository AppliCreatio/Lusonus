package com.example.organisemedia.Screens.PlaylistDetail

import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organisemedia.Helper.Playlist.NewPlaylistDialog
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.organisemedia.Layout.MainLayout
import com.example.organisemedia.Navigation.LocalNavController
import com.example.organisemedia.Screens.Playlist.PlaylistContent
import com.example.organisemedia.Screens.PlaylistDetail.Content.MediaPickerContent
import com.example.organisemedia.ViewModels.MediaViewModel
import com.example.organisemedia.ViewModels.PlaylistViewModel

@Composable
fun PlaylistDetailScreen(
    playlistName: String,
    playlistViewModel: PlaylistViewModel,
    mediaViewModel: MediaViewModel
) {
    // Gets playlist files. Will update because of snapshot.
    val playlistFiles = playlistViewModel.getFiles(playlistName)
    val allMediaFiles = mediaViewModel.files

    var showPicker by rememberSaveable { mutableStateOf(false) }

    MainLayout(
        content = {
            PlaylistDetailContent (
                playlistFiles = playlistFiles,
                onRemoveFromPlaylist = {
                    uri ->
                    playlistViewModel.removeFileFromPlaylist(playlistName, uriString = uri)
                }
            )

            // This is the picker to add a media to the playlist.
            if (showPicker) {
                AlertDialog(
                    onDismissRequest = { showPicker = false },
                    confirmButton = {},
                    text = {
                        MediaPickerContent(
                            allMediaFiles = allMediaFiles,
                            playlistFiles = playlistFiles,
                            onAddToPlaylist = { selected ->
                                playlistViewModel.addFilesToPlaylist(
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