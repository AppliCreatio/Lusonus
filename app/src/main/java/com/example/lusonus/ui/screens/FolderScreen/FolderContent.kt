package com.example.lusonus.ui.screens.FolderScreen

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
import com.example.lusonus.data.model.Media
import com.example.lusonus.ui.composables.MediaLibraryComposables.FileRow

@Composable
fun FolderContent(
    folderFiles: List<Media>,
    onClickMedia: (String) -> Unit
) {
    LazyColumn {
        items(folderFiles) { media ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .pointerInput(media) {
                        detectTapGestures(
                            onTap = { onClickMedia(media.name) }
                        )
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
