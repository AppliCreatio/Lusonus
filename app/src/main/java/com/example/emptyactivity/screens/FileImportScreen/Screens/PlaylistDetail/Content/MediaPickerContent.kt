package com.example.emptyactivity.screens.FileImportScreen.Screens.PlaylistDetail.Content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.emptyactivity.screens.FileImportScreen.Screens.Media.Content.FileRowContent

@Composable
fun MediaPickerContent(
    allMediaFiles: List<String>,
    playlistFiles: List<String>,
    onAddToPlaylist: (List<String>) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Filters out the files that have been added.
        val availableFiles = allMediaFiles.filterNot { it in playlistFiles }

        // In the situation where no media was added in the media page.
        if (availableFiles.isEmpty()) {
            Text(
                text = "No media to add available.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // We exit (don't load the rest)
            return
        }

        // Only non added files.
        availableFiles.forEach { uriString ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        // Adds a single file to playlist
                        onAddToPlaylist(listOf(uriString))
                    }
            ) {
                FileRowContent(
                    uri = uriString.toUri(),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}