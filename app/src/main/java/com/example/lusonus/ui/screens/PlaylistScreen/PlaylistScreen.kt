package com.example.lusonus.ui.screens.PlaylistScreen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.data.model.Playlist
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.PlaylistComposables.MediaPicker
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun PlaylistScreen(
    playlistName: String
) {
    val viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModelFactory(playlistName))

    // Gets playlist files. Will update because of snapshot.
    val playlistFiles = viewModel.playlistFiles
    val allMediaFiles = viewModel.mediaFiles

    var showPicker by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }
    MainLayout(
        content = {

            val sortOptions = listOf<MenuItem>(
                MenuItem("Alphabetical") { viewModel.sortMedia("alphabetically") }
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                MinimalDropdownMenu(sortOptions, expanded, { expanded = !it }, Icons.Sharp.Menu)

                OutlinedTextField(
                    value = searchInfo,
                    onValueChange = {
                        searchInfo = it
                        viewModel.searchMedia( searchInfo.lowercase())
                    },
                    label = { Text("Description") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    singleLine = true
                )

            }

            PlaylistContent(
                playlistFiles = playlistFiles,
                removeFromPlaylist = { media ->
                    viewModel.removeFromPlaylist(media)
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