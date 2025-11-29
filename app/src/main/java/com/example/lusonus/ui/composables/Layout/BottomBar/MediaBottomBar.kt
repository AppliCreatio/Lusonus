package com.example.lusonus.ui.composables.Layout.BottomBar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.ui.screens.MediaScreen.MediaViewModel

@RequiresApi(Build.VERSION_CODES.O)
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