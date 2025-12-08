package com.example.lusonus.ui.composables.PlaylistLibraryComposables

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lusonus.R
import com.example.lusonus.ui.composables.DeleteRow
import java.io.InputStream

@Composable
fun PlaylistItem(
    playlistPicture: Uri?,
    playlistName: String,
    onDelete: (String) -> Unit,
    onClick: (String, Uri?) -> Unit,
) {
    var deleteMode by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val containerColor =
        if (deleteMode) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val bitmap =
        try {
            val stream: InputStream? = context.contentResolver.openInputStream(playlistPicture!!)
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
        Column(
            modifier =
                Modifier
                    .combinedClickable(
                        onClick = {
                            if (deleteMode) {
                                onDelete(playlistName)
                                deleteMode = !deleteMode
                            } else {
                                onClick(playlistName, playlistPicture)
                            }
                        },
                        onLongClick = { deleteMode = !deleteMode },
                    ).padding(12.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (deleteMode) {
                DeleteRow()
            } else {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painter, contentDescription = "The image of the playlist $playlistName", modifier = Modifier.background(MaterialTheme.colorScheme.onSurface))

                        Text(
                            text = playlistName,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}
