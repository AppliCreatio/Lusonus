package com.example.emptyactivity.screens.FileImportScreen.Screens.Media

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.screens.FileImportScreen.Screens.Media.Content.MediaItemContent

@Composable
fun MediaContent(
files: List<String>,
onDeleteMedia: (String) -> Unit
) {
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = files) {
            uriString ->
            MediaItemContent(
                uriString = uriString,
                onDelete = onDeleteMedia
            )
        }
    }
}