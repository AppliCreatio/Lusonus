package com.example.lusonus.ui.screens.FolderViewScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.composables.FolderLibraryComposables.FolderItem

@Composable
fun FolderLibraryContent(
    folders: List<String>,
    onDeleteFolder: (String) -> Unit,
    onClickFolder: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(folders) { folderName ->
            FolderItem(
                folderName = folderName,
                onDelete = onDeleteFolder,
                onClick = onClickFolder
            )
        }
    }
}
