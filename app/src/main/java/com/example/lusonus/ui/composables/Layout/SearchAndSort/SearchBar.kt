package com.example.lusonus.ui.composables.Layout.SearchAndSort

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import android.content.res.Configuration

@Composable
fun SearchBar(searchInfo: String, searchAction: (String) -> Unit) {
    OutlinedTextField(
        value = searchInfo,
        onValueChange = {searchAction(it)},
        label = { Text("Search", color = MaterialTheme.colorScheme.onTertiaryContainer) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
        singleLine = true
    )
}