package com.example.lusonus.ui.screens.FolderScreen

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FolderScreen(folderName: String) {
    val navController = LocalNavController.current
    val viewModel: FolderViewModel = viewModel(factory = FolderViewModelFactory(folderName))
    val context = LocalContext.current

    // Uses flow now :D
    val folderFiles by viewModel.folderFiles.collectAsState()

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

            val sortOptions = listOf(
                MenuItem("Alphabetical") { viewModel.sortMedia("alphabetically") },
                MenuItem("Date Added") { viewModel.sortMedia("date added") },
                MenuItem("Last Played") { viewModel.sortMedia("last played") }
            )

            SearchAndSort(
                sortOptions, expanded, { expanded = !it }, searchInfo, {
                    searchInfo = it
                    viewModel.searchMedia(it.lowercase())
                }
            )

            FolderContent(
                folderFiles = folderFiles,
                onClickMedia = { mediaName ->
                    navController.navigate(Routes.MediaPlayer.go(mediaName))
                }
            )
        },

        screenTitle = folderName
    )
}
