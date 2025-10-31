package com.example.lusonus.ui.screens.MediaScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryContent
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun MediaScreen(mediaName: String = "Media Name", mediaArtist: String = "Artist Name") {
    MainLayout(
        content = {
            MediaContent(mediaName = mediaName, mediaArtist = mediaArtist)
        },
        screenTitle = "Media"
    )
}