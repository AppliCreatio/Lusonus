package com.example.lusonus.ui.composables.Layout.Buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.lusonus.data.model.MenuItem

/**
 * A reusable drop down composable that is used when in need on an "options" button.
 * I decide the contents that are used for this dropdown when calling the composable.
 */
@Composable
fun MinimalDropdownMenu(menuItems: List<MenuItem>) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.title) },
                    onClick = { option.action() }
                )
            }
        }
    }
}