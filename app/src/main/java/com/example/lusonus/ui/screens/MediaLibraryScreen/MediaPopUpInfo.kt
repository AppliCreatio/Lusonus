package com.example.lusonus.ui.screens.MediaLibraryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MediaPopUpInfo (mediaName: String, mediaArtist: String) {
    Column {
        Text(
            text = mediaName,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = mediaArtist,
            style = MaterialTheme.typography.labelSmall
        )
    }
}