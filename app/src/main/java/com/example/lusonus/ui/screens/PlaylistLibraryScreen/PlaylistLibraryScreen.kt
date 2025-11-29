package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.Layout.TopBar.SharedNavTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar
import com.example.organisemedia.Helper.Playlist.NewPlaylistDialog
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlaylistLibraryScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the view model information
    val viewModel: PlaylistLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity)

    // Gets files from view model.
    val playlists by viewModel.playlists.collectAsState()

    // State variables.
    var showDialog by rememberSaveable { mutableStateOf(value = false) }
    var expanded by rememberSaveable { mutableStateOf(false) }
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
        topBar = {
            Column {
                SharedTopBar("Lusonus")
                SharedNavTopBar()
            }
        },
        content = {

            val sortOptions = listOf(
                MenuItem("Alphabetical") { viewModel.sortPlaylist("alphabetically") },
                MenuItem("Date Added") { viewModel.sortPlaylist("date added") },
                MenuItem("Last Played") { viewModel.sortPlaylist("last played") }
            )

            SearchAndSort(sortOptions, expanded, { expanded = !it }, searchInfo, {
                searchInfo = it
                viewModel.searchPlaylists( searchInfo.lowercase())
            })

            PlaylistLibraryContent(
                playlists = playlists.map {it.name},
                onDeletePlaylist = { playlistName ->
                    viewModel.deletePlaylist(playlistName)
                },
                onClickPlaylist = { playlistName ->
                    navController.navigate(Routes.Playlist.createRoute(playlistName))
                }
            )
        },
        screenTitle = "Lusonus",
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = {
                    showDialog = true
                }
            )
        }
    )
}