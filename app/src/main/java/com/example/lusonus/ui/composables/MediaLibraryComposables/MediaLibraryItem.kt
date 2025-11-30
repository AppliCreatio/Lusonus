package com.example.lusonus.ui.composables.MediaLibraryComposables

import FileRow
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.model.Media

@Composable
fun MediaLibraryItem(
    media: Media,
    onDelete: (Uri) -> Unit,
    onClick: (String) -> Unit,
) {
    var deleteMode by rememberSaveable { mutableStateOf(false) }

    val containerColor =
        if (deleteMode) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.surfaceVariant

    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        if (deleteMode) {
                            onDelete(media.uri)
                            deleteMode = !deleteMode
                        }
                        else {
                            onClick(media.name)
                        }
                    },
                    onLongClick = { deleteMode = !deleteMode }
                ).padding(8.dp)
        ) {
            if (deleteMode)
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                FileRow(uri = media.uri)
            }
        }
    }
}