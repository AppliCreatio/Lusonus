package com.example.lusonus.ui.composables.MediaLibraryComposables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun MediaLibraryItem(
    uriString: String,
    onDelete: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            // THIS IS SO COOL! It's how you can check for long presses!
            .pointerInput(Unit) {
                detectTapGestures(
                    // We specify a long press.
                    onLongPress = {
                        onDelete(uriString)
                    }
                )
            }
    ) {
        FileRow(
            uri = uriString.toUri(),
            modifier = Modifier.padding(12.dp)
        )
    }
}