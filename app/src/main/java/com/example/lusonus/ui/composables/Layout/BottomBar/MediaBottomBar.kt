package com.example.lusonus.ui.composables.Layout.BottomBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.ui.screens.MediaScreen.MediaViewModel

@Composable
fun MediaBottomBar() {
    val viewModel: MediaViewModel = viewModel()

    // Calls the MediaBottomBarStateless, the :: allows the passing of the function itself.
    MediaBottomBarStateless(
        isLiked = viewModel.isLiked,
        isPaused = viewModel.isPlaying,
        onToggleQueue = viewModel::toggleQueue,
        onToggleLike = viewModel::toggleLike,
        onTogglePause = viewModel::togglePause
    )
}