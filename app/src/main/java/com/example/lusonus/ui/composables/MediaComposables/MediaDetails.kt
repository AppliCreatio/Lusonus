package com.example.lusonus.ui.composables.MediaComposables

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lusonus.data.dataclasses.Media

@Composable
fun MediaDetails(
    media: Media,
    image: Painter
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .size(350.dp)
        )
        Text(
            text = media.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(5.dp, 2.dp, 5.dp),
            fontSize = 60.sp,
        )
        Text(
            // TODO: change this
            text = media.name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier,
            fontSize = 25.sp
        )
    }
}