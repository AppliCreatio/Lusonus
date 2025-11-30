package com.example.organisemedia.Helper.Playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NewPlaylistDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    // So the playlist name is saveable.
    var playlistName by rememberSaveable { mutableStateOf(value = "") }

    var showError by rememberSaveable { mutableStateOf(false) }

    if (!showDialog) return

    // If there's a dialog to show.
    AlertDialog(
        // Title of the popup.
        title = {
            Text(
                text = "Create Playlist",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        },

        // The input box of the popup.
        text = {
            Column {
                OutlinedTextField(
                    value = playlistName,
                    onValueChange = {
                        playlistName = it
                        showError = false
                    },
                    label = { Text("Playlist Name") },
                    isError = showError,
                    singleLine = true,
                )

                if (showError) {
                    Text(
                        text = "Playlist name cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        },

        // The confirm button.
        confirmButton = {
            Button(
                onClick = {
                    if (playlistName.isBlank()) {
                        showError = true
                    } else {
                        onConfirm(playlistName.trim())
                        playlistName = ""
                        showError = false
                    }
                }
            ) {
                Text("Create")
            }
        },

        // The dismiss button.
        dismissButton = {
            TextButton(
                onClick = {
                    playlistName = ""
                    showError = false
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancel")
            }
        },

        // Action when dismissed.
        // e.g. someone click off the sides.
        onDismissRequest = {
            showError = false
            onDismiss()
        },
        shape = RoundedCornerShape(20.dp),
    )
}
