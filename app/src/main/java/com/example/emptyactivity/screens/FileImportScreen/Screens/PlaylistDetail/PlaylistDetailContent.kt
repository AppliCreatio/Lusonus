package com.example.emptyactivity.screens.FileImportScreen.Screens.PlaylistDetail

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
import com.example.emptyactivity.screens.FileImportScreen.Screens.Media.Content.FileRowContent

@Composable
fun PlaylistDetailContent(
    playlistFiles: List<String>,
    onRemoveFromPlaylist: (String) -> Unit
) {
    LazyColumn {
        items(items = playlistFiles) {
            uriString ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    // THIS IS SO COOL! It's how you can check for long presses!
                    .pointerInput(Unit) {
                        detectTapGestures(
                            // We specify a long press.
                            onLongPress = {
                                onRemoveFromPlaylist(uriString)
                            },
                        )
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