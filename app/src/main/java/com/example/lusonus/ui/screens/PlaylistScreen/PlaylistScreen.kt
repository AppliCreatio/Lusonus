package com.example.lusonus.ui.screens.PlaylistScreen

import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.Playlist
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.PlaylistComposables.MediaPicker
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun PlaylistScreen(
    playlistName: String
) {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the playlist view model, calls the media factory so we can pass the playlist name to the
    // view model to be able to get the specific playlist.
    val viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModelFactory(playlistName))

    // Gets playlist files. Will update because of snapshot.
    val playlistFiles = viewModel.playlistFiles
    val allMediaFiles = viewModel.mediaFiles

    var showPicker by rememberSaveable { mutableStateOf(false) }

    MainLayout(
        content = {
            PlaylistContent(
                playlistFiles = playlistFiles,
                removeFromPlaylist = { media ->
                    viewModel.removeFromPlaylist(media)
                },
                onClickMedia = { mediaName ->
                    navController.navigate(Routes.MediaPlayer.go(mediaName))
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
                                viewModel.addToPlaylist(selected)
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