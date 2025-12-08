package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.dataclasses.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.Layout.TopBar.SharedNavTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.TopBarAddButton
import com.example.organisemedia.Helper.Playlist.NewPlaylistDialog

@Composable
fun PlaylistLibraryScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the view model information
    val viewModel: PlaylistLibraryViewModel =
        viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity)

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
            onConfirm = { name ->
                viewModel.createPlaylist(name)
                showDialog = false
            },
        )
    }

    MainLayout(
        content = {
            val sortOptions =
                listOf(
                    MenuItem(
                        title = "Alphabetical",
                        action = { viewModel.sortPlaylist("alphabetically") },
                    ),
                    MenuItem(title = "Date Added", action = { viewModel.sortPlaylist("date added") }),
                    MenuItem(title = "Last Played", action = { viewModel.sortPlaylist("last played") }),
                )

            SearchAndSort(sortOptions, expanded, { expanded = !it }, searchInfo, {
                searchInfo = it
                viewModel.searchPlaylists(searchInfo.lowercase())
            })

            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .offset(y = 40.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                    ),
            ) {
                PlaylistLibraryContent(
                    playlists = playlists,
                    onDeletePlaylist = { playlistName ->
                        viewModel.deletePlaylist(playlistName)
                    },
                    onClickPlaylist = { playlistName, playlistPicture ->
                        navController.navigate(Routes.Playlist.createRoute(playlistName,playlistPicture))
                    },
                )
            }
        },
        screenTitle = "Lusonus",
        topBar = {
            Column {
                SharedTopBar("Lusonus", {
                    TopBarAddButton(onClick = {
                        showDialog = true
                    })
                })
                SharedNavTopBar()
            }
        },
    )
}
