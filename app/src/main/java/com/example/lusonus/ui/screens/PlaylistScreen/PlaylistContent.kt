package com.example.lusonus.ui.screens.PlaylistScreen

import FileRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.model.Media

@Composable
fun PlaylistContent(
    playlistFiles: List<Media>,
    removeFromPlaylist: (Media) -> Unit,
    onClickMedia: (String) -> Unit
) {
    LazyColumn {
        items(items = playlistFiles) {
            media ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onClickMedia(media.name) }
                        .combinedClickable(
                            onClick = { onClickMedia(media.name) },
                            onLongClick = { removeFromPlaylist(media) }
                        ).padding(8.dp)
                ) {
                    FileRow(uri = media.uri)
                }
            }
        }
    }
}