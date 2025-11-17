package com.example.lusonus.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable

@Composable
fun SearchBar(searchInfo: String, searchAction: (String) -> Unit) {
        OutlinedTextField(
            value = searchInfo,
            onValueChange = {searchAction(it)},
            label = { Text("Description") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            singleLine = true
        )
}