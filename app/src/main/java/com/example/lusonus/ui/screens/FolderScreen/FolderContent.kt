package com.example.lusonus.ui.screens.FolderScreen

import FileRow
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.ui.utils.getMimeType

@Composable
fun FolderContent(
    folderFiles: List<Media>,
    onClickMedia: (String) -> Unit,
) {
    val context = LocalContext.current
    val global = LocalGlobals.current
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(folderFiles) { media ->
            val mimeType = context.getMimeType(media.uri)

            val isAudio = mimeType.startsWith("audio")
            val isVideo = mimeType.startsWith("video")

            if (global.settings.fileTypeRestriction == 1 && isAudio)
                return@items

            if (global.settings.fileTypeRestriction == 2 && isVideo)
                return@items

            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .combinedClickable(
                                onClick = { onClickMedia(media.name) },
                            ).padding(12.dp),
                ) {
                    FileRow(
                        uri = media.uri,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
