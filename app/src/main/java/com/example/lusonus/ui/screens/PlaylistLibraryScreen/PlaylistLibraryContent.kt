package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.composables.PlaylistLibraryComposables.PlaylistItem

@Composable
fun PlaylistLibraryContent(
    playlists: List<String>,
    onDeletePlaylist: (String) -> Unit,
    onClickPlaylist: (String) -> Unit
) {
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding( 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = playlists) {
            playlistName ->
            PlaylistItem(
                playlistName = playlistName,
                onDelete = onDeletePlaylist,
                onClick = onClickPlaylist
            )
        }
    }
}