package com.example.lusonus.ui.screens.MediaScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.BottomBar.MediaBottomBar
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryContent
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistScreen.PlaylistViewModel
import com.example.lusonus.ui.screens.PlaylistScreen.PlaylistViewModelFactory
import com.example.lusonus.ui.theme.AppTheme

@Composable
fun MediaScreen(mediaName: String) {
    // Gets the media view model, calls the media factory so we can pass the media name to the
    // view model to be able to get the specific media.
    val viewModel: MediaViewModel = viewModel(factory = MediaViewModelFactory(mediaName))

    MainLayout(
        content = {
            MediaContent(viewModel.media!!, viewModel.isQueueOpen)
        },
        screenTitle = mediaName,
        bottomBar = { MediaBottomBar() }
    )
}