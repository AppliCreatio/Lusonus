package com.example.lusonus.ui.screens.MediaLibraryScreen

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lusonus.R

@Composable
fun MediaPopUpControls(isPaused: Boolean) {
    IconButton(
        onClick = {
            // TODO: Implement previous media functionality
        },
        modifier = Modifier,
        enabled = false) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.previous_button),
            contentDescription = "A button to skip to the previous song."
        )
    }

    IconButton(
        modifier = Modifier,
        onClick = {
            // TODO: Implement pause functionality
        }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = if (isPaused) painterResource(R.drawable.pause_button) else painterResource(R.drawable.play_button),
            contentDescription = "A button to play and pause the current song."
        )
    }

    IconButton(
        onClick = {
            // TODO: Implement next media functionality
        },
        modifier = Modifier,
        enabled = false) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.next_button),
            contentDescription = "A button to skip to the next song.",
        )
    }
}