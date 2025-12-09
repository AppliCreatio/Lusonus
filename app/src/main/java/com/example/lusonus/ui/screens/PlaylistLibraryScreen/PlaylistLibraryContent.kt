package com.example.lusonus.ui.screens.PlaylistLibraryScreen

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.dataclasses.Playlist
import com.example.lusonus.ui.composables.PlaylistLibraryComposables.PlaylistItem

/*
*   Brandon made this file with changes by Alex
*  */

@Composable
fun PlaylistLibraryContent(
    playlists: List<Playlist>,
    onDeletePlaylist: (String) -> Unit,
    onClickPlaylist: (String, Uri?) -> Unit,
) {
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = playlists) { playlist ->
            PlaylistItem(
                playlistPicture = playlist.image,
                playlistName = playlist.name,
                onDelete = onDeletePlaylist,
                onClick = onClickPlaylist,
            )
        }
    }
}
