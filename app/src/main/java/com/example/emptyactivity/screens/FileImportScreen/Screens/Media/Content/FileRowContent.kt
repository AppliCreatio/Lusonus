package com.example.emptyactivity.screens.FileImportScreen.Screens.Media.Content

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.emptyactivity.screens.FileImportScreen.Helper.File.getFileName

// Displays files as rows.
@Composable
fun FileRowContent(uri: Uri, modifier: Modifier = Modifier) {
    // The way to get access to android system services.
    val context = LocalContext.current

    val name = remember(uri) {
        // Sets it to Unknown if it can't get the name... error handling basically.
        runCatching {
            context.getFileName(uri)
        }.getOrElse { "Unknown" }
    }

    // Displays file information.
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}