package com.example.lusonus.ui.composables.PlaylistComposables

import FileRow
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.ui.utils.getMimeType

/*
*   Brandon made this entire file
*  */

@Composable
fun MediaPicker(
    allMediaFiles: List<Media>,
    playlistFiles: List<Media>,
    onAddToPlaylist: (List<Media>) -> Unit,
) {
    val context = LocalContext.current
    val global = LocalGlobals.current

    val availableFiles =
        allMediaFiles.filter { media ->
            playlistFiles.none { it.uri == media.uri }
        }

    var selectionMode by rememberSaveable { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Media>() }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (selectionMode) "Select Media" else "Add Media",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (availableFiles.isEmpty()) {
            Text(
                text = "No media to add",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            return
        }

        if (selectionMode) {
            // cute button
            FilledTonalButton(
                onClick = {
                    onAddToPlaylist(selectedItems.toList())
                },
            ) {
                Text("Add (${selectedItems.size})")
            }
        }

        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            modifier =
                Modifier
                    .fillMaxSize()
                    .offset(y = 40.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                ),
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(availableFiles) { media ->
                    val mimeType = context.getMimeType(media.uri)

                    val isAudio = mimeType.startsWith("audio")
                    val isVideo = mimeType.startsWith("video")

                    if (global.settings.fileTypeRestriction == 1 && isAudio)
                        return@items

                    if (global.settings.fileTypeRestriction == 2 && isVideo)
                        return@items

                    val isSelected = media in selectedItems

                    Card(
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    },
                            ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = MaterialTheme.shapes.medium,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .combinedClickable(
                                    onClick = {
                                        if (selectionMode) {
                                            if (isSelected) {
                                                selectedItems.remove(media)
                                            } else {
                                                selectedItems.add(media)
                                            }

                                            // Exit selection mode if none left.
                                            if (selectedItems.isEmpty()) {
                                                selectionMode = false
                                            }
                                        } else {
                                            // Normal single add
                                            onAddToPlaylist(listOf(media))
                                        }
                                    },
                                    onLongClick = {
                                        if (!selectionMode) {
                                            // Enter multi select.
                                            selectionMode = true
                                            selectedItems.add(media)
                                        } else {
                                            // Exit multi select.
                                            selectionMode = false
                                            selectedItems.clear()
                                        }
                                    },
                                ),
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            FileRow(uri = media.uri)
                        }
                    }
                }
            }
        }
    }
}
