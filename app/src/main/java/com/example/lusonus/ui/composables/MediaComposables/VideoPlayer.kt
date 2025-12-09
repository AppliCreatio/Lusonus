package com.example.lusonus.ui.composables.MediaComposables

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

/*
*   Brandon made this entire file
*  */

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(player: ExoPlayer, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                this.useController = false
            }
        },
        modifier = modifier
    )
}

