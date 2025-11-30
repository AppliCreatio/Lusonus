package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.model.Media
import com.example.lusonus.ui.composables.MediaLibraryComposables.MediaLibraryItem

@Composable
fun MediaLibraryContent(
    files: List<Media>,
    onDeleteMedia: (Uri) -> Unit,
    onClickMedia: (String) -> Unit
) {
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = files) {
            media ->
            MediaLibraryItem(
                media = media,
                onDelete = onDeleteMedia,
                onClick = onClickMedia
            )
        }
    }
}