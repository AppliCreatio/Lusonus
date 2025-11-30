package com.example.lusonus.ui.composables.PlaylistComposables

import FileRow
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
import com.example.lusonus.data.model.Media

@Composable
fun MediaPicker(
    allMediaFiles: List<Media>,
    playlistFiles: List<Media>,
    onAddToPlaylist: (List<Media>) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Filters out the files that have been added.
        val availableFiles = allMediaFiles.filter { media ->
            playlistFiles.none { it.uri == media.uri }
        }

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
        availableFiles.forEach { media ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        // Adds a single file to playlist
                        onAddToPlaylist(listOf(media))
                    }
            ) {
                FileRow(
                    uri = media.uri,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}