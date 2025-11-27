package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lusonus.R


@Composable
fun MediaPopUpInfo (mediaName: String, mediaArtist: String, mediaImage: Bitmap?) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        AsyncImage(
            model = mediaImage?.asImageBitmap() ?: R.drawable.resource_default,
            contentDescription = "Artwork representing the $mediaName media.",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .size(40.dp)
        )

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

}