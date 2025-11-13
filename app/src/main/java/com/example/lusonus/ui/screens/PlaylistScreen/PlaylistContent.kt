package com.example.lusonus.ui.screens.PlaylistScreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.lusonus.data.model.Media
import com.example.lusonus.ui.composables.MediaLibraryComposables.FileRow

@Composable
fun PlaylistContent(
    playlistFiles: List<Media>,
    removeFromPlaylist: (Media) -> Unit,
    onClickMedia: (String) -> Unit
) {
    LazyColumn {
        items(items = playlistFiles) {
            media ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    // THIS IS SO COOL! It's how you can check for long presses!
                    .pointerInput(media) {
                        detectTapGestures(
                            // We specify a long press.
                            onLongPress = {
                                removeFromPlaylist(media)
                            },

                            // AND THEN WE CAN JUST HAVE... ITS AMAZING!
                            onTap = {
                                onClickMedia(media.name)
                            }
                        )
                    }
            ) {
                // TODO: need to replace this with entrydisplay when we do do it.
                FileRow(
                    uri = media.uri,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}