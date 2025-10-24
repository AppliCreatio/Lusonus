package com.example.organisemedia.Screens.Playlist

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organisemedia.Helper.Playlist.NewPlaylistDialog
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.organisemedia.Layout.MainLayout
import com.example.organisemedia.Navigation.LocalNavController
import com.example.organisemedia.Navigation.Routes
import com.example.organisemedia.Screens.Media.MediaContent
import com.example.organisemedia.ViewModels.MediaViewModel
import com.example.organisemedia.ViewModels.PlaylistViewModel

@Composable
fun PlaylistScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the view model information
    val viewModel: PlaylistViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.

    // Gets files from view model.
    val playlists = viewModel.getAllPlaylists()

    // State variables.
    var showDialog by rememberSaveable { mutableStateOf(value = false) }

    // If dialog should be shown.
    if (showDialog) {
        // Prompt new playlist dialog.
        NewPlaylistDialog(
            // It would always be true in this scenario.
            showDialog = true,

            // What happens when dismissed.
            onDismiss = {
                showDialog = false
            },

            // What happens on confirmation.
            onConfirm = {
                name ->
                viewModel.createPlaylist(name)
                showDialog = false
            }
        )
    }

    MainLayout(
        content = {
            PlaylistContent(
                playlists = playlists,
                onDeletePlaylist = {
                    playlistName ->
                    viewModel.deletePlaylist(playlistName)
                },
                onClickPlaylist = {
                    playlistName ->
                    navController.navigate(Routes.PlaylistDetail.createRoute(playlistName))
                }
            )
        },
        screenTitle = "Playlists",
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = {
                    showDialog = true
                }
            )
        }
    )
}