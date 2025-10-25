package com.example.emptyactivity.screens.FileImportScreen.Screens.Playlist.Content

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun PlaylistItemContent(
    playlistName: String,
    onDelete: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            // THIS IS SO COOL! It's how you can check for long presses!
            .pointerInput(Unit) {
                detectTapGestures(
                    // We specify a long press.
                    onLongPress = {
                        onDelete(playlistName)
                    },

                    // AND THEN WE CAN JUST HAVE... ITS AMAZING!
                    onTap = {
                        onClick(playlistName)
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = playlistName, style = MaterialTheme.typography.bodyLarge)
        }
    }
}