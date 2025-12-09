package com.example.lusonus.ui.composables.PlaylistComposables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PlaylistBanner(painter: Painter, playlistName: String, onImageClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = "$playlistName's image",
            modifier =
                Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(onClick = onImageClick)
                    .background(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.Crop,
        )

        Text(text = playlistName, fontWeight = FontWeight.Bold)
    }
}