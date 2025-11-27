package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.screens.MediaScreen.MediaViewModel
import com.example.lusonus.ui.screens.MediaScreen.MediaViewModelFactory

@Composable
fun MediaPopUpScreen(mediaName: String) {
    val viewModel: MediaViewModel = viewModel(factory = MediaViewModelFactory(mediaName))

    // Gets nav controller
    val navController = LocalNavController.current

    MediaPopUpContent(
        media = viewModel.media!!,
        bitmapImage = viewModel.artworkBitmap ,
        isPlaying = viewModel.isPlaying,
        onClickPopUp = { mediaName ->
            navController.navigate(Routes.MediaPlayer.go(mediaName))
        })
}