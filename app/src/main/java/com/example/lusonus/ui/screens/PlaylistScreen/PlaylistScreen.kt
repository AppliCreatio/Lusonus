package com.example.lusonus.ui.screens.PlaylistScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.dataclasses.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.TopBarAddButton
import com.example.lusonus.ui.composables.PlaylistComposables.MediaPicker

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlaylistScreen(playlistName: String) {
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
        val observer =
            LifecycleEventObserver { _, event ->
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

            val sortOptions =
                listOf(
                    MenuItem(
                        title = "Alphabetical",
                        action = { viewModel.sortMedia("alphabetically") },
                    ),
                    MenuItem(title = "Date Added", action = { viewModel.sortMedia("date added") }),
                    MenuItem(title = "Last Played", action = { viewModel.sortMedia("last played") }),
                )

            SearchAndSort(sortOptions, expanded, { expanded = !it }, searchInfo, {
                searchInfo = it
                viewModel.searchMedia(searchInfo.lowercase())
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
                PlaylistContent(
                    playlistFiles = playlistFiles,
                    removeFromPlaylist = { media ->
                        viewModel.removeFromPlaylist(media)
                    },
                    onClickMedia = { mediaName ->
                        navController.navigate(Routes.MediaPlayer.go(mediaName))
                    },
                )
            }

            // This is the picker to add a media to the playlist.
            if (showPicker) {
                ModalBottomSheet(
                    onDismissRequest = { showPicker = false },
                ) {
                    MediaPicker(
                        allMediaFiles = allMediaFiles,
                        playlistFiles = playlistFiles,
                        onAddToPlaylist = { media ->
                            viewModel.addToPlaylist(media)
                            showPicker = false
                        },
                    )
                }
            }
        },
        screenTitle = playlistName,
        topBar = {
            Column {
                SharedTopBar(playlistName, {
                    TopBarAddButton(onClick = {
                        showPicker = true
                    })
                })
            }
        },
    )
}
