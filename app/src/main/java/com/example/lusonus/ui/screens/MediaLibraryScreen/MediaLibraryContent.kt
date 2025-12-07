package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.ui.composables.MediaLibraryComposables.MediaLibraryItem
import com.example.lusonus.ui.utils.getMimeType

@Composable
fun MediaLibraryContent(
    files: List<Media>,
    onDeleteMedia: (Uri) -> Unit,
    onClickMedia: (String) -> Unit,
) {
    val context = LocalContext.current
    val global = LocalGlobals.current
    // Lazy column in-case can have a lot of files added
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = files) { media ->
            val mimeType = context.getMimeType(media.uri)

            val isAudio = mimeType.startsWith("audio")
            val isVideo = mimeType.startsWith("video")

            if (global.settings.fileTypeRestriction == 1 && isAudio)
                return@items

            if (global.settings.fileTypeRestriction == 2 && isVideo)
                return@items

            MediaLibraryItem(
                media = media,
                onDelete = onDeleteMedia,
                onClick = onClickMedia,
            )
        }
    }
}
