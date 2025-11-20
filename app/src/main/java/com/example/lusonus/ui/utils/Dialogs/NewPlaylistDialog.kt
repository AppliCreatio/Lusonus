package com.example.organisemedia.Helper.Playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun NewPlaylistDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    // So the playlist name is saveable.
    var playlistName by rememberSaveable { mutableStateOf(value = "") }

    // If there's a dialog to show.
    if (showDialog) {
        AlertDialog(
            // Title of the popup.
            title = {
                Text("New Playlist")
            },

            // The input box of the popup.
            text = {
                Column {
                    // Fancy text field.
                    OutlinedTextField(
                        // We are creating a playlist so...
                        value = playlistName,
                        onValueChange = { playlistName = it },
                        label = {
                            Text("Playlist Name")
                        },
                        singleLine = true
                    )
                }
            },

            // The confirm button.
            confirmButton = {
                Button(
                    onClick = {
                        if (playlistName.isNotBlank()) {
                            // Action when the confirm button is clicked.
                            onConfirm(playlistName)
                            playlistName = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            },

            // The dismiss button.
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            },

            // Action when dismissed.
            // e.g. someone click off the sides.
            onDismissRequest = onDismiss
        )
    }
}
