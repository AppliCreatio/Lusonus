package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalCurrentMedia
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.BottomBar.SharedBottomBar
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.MediaComposables.MediaPopUp.MediaPopUpScreen
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton

@Composable
fun MediaLibraryScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the current playing media
    var currentMedia = LocalCurrentMedia.current

    // Gets the view model information
    val viewModel: MediaLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.

    val context = LocalContext.current

    // Gets files from view model... but as a hot flow!

    val files by viewModel.mediaFiles.collectAsState()

    // This is a very fancy thing!
    // It uses this thing called Activity Result API,
    // which basically allows you to open the default file picker.
    // I had an issue where you couldn't select multiple files,
    // but then I found OpenMultipleDocuments which does just that.
    // After the user selects files, it adds them to a List<Uri>? (which is "basically" pointers)
    // We add them to selectedFile (is below) so we can display them!
    val pickFilesLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris: List<Uri>? ->
            // Reminder: .let lets you use "it" to refer to the thing "." is appended to...
            // In this case "uris?".
            uris?.let {
                // We map over each uris and convert it to a string.
                // Has to be in strings because you can't use rememberSaveable on uris.
                viewModel.addFiles(context,it)
            }
        }

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
                MediaLibraryContent(
                    files = files,
                    onDeleteMedia = { uri ->
                        viewModel.removeFile(uri)
                    },
                    onClickMedia = { mediaName ->
//                        navController.navigate(Routes.MediaPlayer.go(mediaName))
                        currentMedia = mediaName
                    }
                )
                  },
        screenTitle = "Media",
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = {
                    // Launch the file picker
                    pickFilesLauncher.launch(input = arrayOf("audio/*", "video/*"))
                }
            )
        },
        bottomBar = {
            Column {
                // Only shows up when there is an actual value associated to it
                if(currentMedia.isNotEmpty())
                    MediaPopUpScreen(currentMedia)
                SharedBottomBar()
            }
        }
    )
}