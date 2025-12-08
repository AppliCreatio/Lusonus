package com.example.lusonus.ui.screens.PlaylistScreen

import FileRow
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lusonus.R
import com.example.lusonus.data.dataclasses.Media
import java.io.InputStream

@Composable
fun PlaylistContent(
    playlistName: String,
    playlistImage: Uri?,
    playlistFiles: List<Media>,
    removeFromPlaylist: (Media) -> Unit,
    onClickMedia: (String) -> Unit,
    onImageClick: () -> Unit
) {

    val context = LocalContext.current

    val bitmap =
        try {
            val stream: InputStream? = context.contentResolver.openInputStream(playlistImage!!)
            BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            null
        }

    val painter =
        if (bitmap != null) {
            BitmapPainter(bitmap.asImageBitmap())
        } else {
            painterResource(id = R.drawable.lusonus_placeholder)
        }

    LazyColumn(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        item {
            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Image(
                    painter = painter,
                    contentDescription = "File artwork",
                    modifier =
                        Modifier
                            .size(128.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(onClick = onImageClick).background(MaterialTheme.colorScheme.onSurface),
                    contentScale = ContentScale.Crop,
                )

                Text(text = playlistName, fontWeight = FontWeight.Bold)
            }
        }

        items(items = playlistFiles) { media ->
            var deleteMode by remember { mutableStateOf(false) }

            val containerColor =
                if (deleteMode) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = containerColor,
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .clickable { onClickMedia(media.name) }
                            .combinedClickable(
                                onClick = {
                                    if (deleteMode) {
                                        removeFromPlaylist(media)
                                        deleteMode = !deleteMode
                                    } else {
                                        onClickMedia(media.name)
                                    }
                                },
                                onLongClick = { deleteMode = !deleteMode },
                            ).padding(8.dp),
                ) {
                    if (deleteMode) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(64.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.onError,
                                modifier = Modifier.size(48.dp),
                            )
                        }
                    } else {
                        FileRow(uri = media.uri)
                    }
                }
            }
        }
    }
}
