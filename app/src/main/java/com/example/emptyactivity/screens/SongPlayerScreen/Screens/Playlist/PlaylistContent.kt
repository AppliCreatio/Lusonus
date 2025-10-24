package com.example.organisemedia.Screens.Playlist

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.organisemedia.Screens.Media.Content.FileRowContent
import com.example.organisemedia.Screens.Playlist.Content.PlaylistItemContent

@Composable
fun PlaylistContent(
    playlists: List<String>,
    onDeletePlaylist: (String) -> Unit,
    onClickPlaylist: (String) -> Unit
) {
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = playlists) {
            playlistName ->
            PlaylistItemContent(
                playlistName = playlistName,
                onDelete = onDeletePlaylist,
                onClick = onClickPlaylist
            )
        }
    }
}