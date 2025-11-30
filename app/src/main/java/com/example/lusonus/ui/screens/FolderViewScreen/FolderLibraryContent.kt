package com.example.lusonus.ui.screens.FolderViewScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.model.Folder
import com.example.lusonus.ui.composables.FolderLibraryComposables.FolderItem
import com.example.lusonus.ui.utils.getName

@Composable
fun FolderLibraryContent(
    folders: List<Folder>,
    onDeleteFolder: (String) -> Unit,
    onClickFolder: (String) -> Unit
) {
    // Gets the local context.
    val context = LocalContext.current

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(folders) { folder ->
                FolderItem(
                    folderName = folder.name,
                    folderPath = context.getName(folder.uri),
                    onDelete = onDeleteFolder,
                    onClick = onClickFolder
                )
            }
        }
}
