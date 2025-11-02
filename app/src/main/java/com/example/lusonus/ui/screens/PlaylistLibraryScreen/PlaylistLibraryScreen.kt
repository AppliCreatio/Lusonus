package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.organisemedia.Helper.Playlist.NewPlaylistDialog
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun PlaylistLibraryScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the view model information
    val viewModel: PlaylistLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.

    // Gets files from view model.
    val playlists = viewModel.getAllPlaylists()

    // State variables.
    var showDialog by rememberSaveable { mutableStateOf(value = false) }
    var expanded by remember { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }

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

            val sortOptions = listOf<MenuItem>(
                MenuItem("Alphabetical", { viewModel.sortPlaylist("alphabetically")})
            )

            Row() {
                MinimalDropdownMenu(sortOptions, expanded, { expanded = !it }, Icons.Sharp.Menu)
                OutlinedTextField(
                    value = searchInfo,
                    onValueChange = { searchInfo = it },
                    label = { Text("Description") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    singleLine = true
                )
            }

            PlaylistLibraryContent(
                playlists = playlists,
                onDeletePlaylist = { playlistName ->
                    viewModel.deletePlaylist(playlistName)
                },
                onClickPlaylist = { playlistName ->
                    navController.navigate(Routes.Playlist.createRoute(playlistName))
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