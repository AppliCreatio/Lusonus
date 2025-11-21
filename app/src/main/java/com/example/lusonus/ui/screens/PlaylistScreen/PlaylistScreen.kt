package com.example.lusonus.ui.screens.PlaylistScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.PlaylistComposables.MediaPicker
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchBar

@Composable
fun PlaylistScreen(
    playlistName: String
) {
    // Gets nav controller
    val navController = LocalNavController.current

    val context = LocalContext.current

    // Gets the playlist view model, calls the media factory so we can pass the playlist name to the
    // view model to be able to get the specific playlist.
    val viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModelFactory(playlistName))

    val playlistFiles by viewModel.playlistFiles.collectAsState()
    val allMediaFiles by viewModel.allMediaFiles.collectAsState()

    var showPicker by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }

    val lifecycleOwner = LocalLifecycleOwner.current

    // Refreshes when the screen appears and when the app returns to the foreground.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // User returned to the app while screen is active.
                viewModel.refreshMedia(context)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    MainLayout(
        content = {
            LaunchedEffect(Unit) {
                viewModel.refreshMedia(context)
            }

            val sortOptions = listOf<MenuItem>(
                MenuItem("Alphabetical") { viewModel.sortMedia("alphabetically") }
            )

            SearchAndSort(sortOptions, expanded, { expanded = !it }, searchInfo, {
                searchInfo = it
                viewModel.searchMedia( searchInfo.lowercase())
            })

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