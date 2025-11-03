package com.example.lusonus.ui.screens.MediaScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
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
import com.example.lusonus.ui.theme.AppTheme

@Composable
fun MediaScreen(mediaName: String = "Media Name", mediaArtist: String = "Artist Name") {
    var isQueueOpen by rememberSaveable { mutableStateOf(false) }

    MainLayout(
        content = {
            MediaContent(mediaName = mediaName, mediaArtist = mediaArtist, isQueueOpen = isQueueOpen)
        },
        screenTitle = mediaName,
        bottomBar = { MediaBottomBar(isQueueOpen) }
    )
}

@Preview
@Composable
fun MediaScreenPreview () {
    AppTheme {
        MediaScreen("Crazy Train", "Ozzy Osbourne")
    }
}