package com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lusonus.data.model.MenuItem

/**
 * A reusable drop down composable that is used when in need on an "options" button.
 * I decide the contents that are used for this dropdown when calling the composable.
 */
@Composable
fun MinimalDropdownMenu(menuItems: List<MenuItem>, expanded: Boolean, toggleAction: (Boolean) -> Unit, icon: ImageVector) {

    Box {
        IconButton(onClick = { toggleAction(expanded) } ) {
            Icon(icon, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { toggleAction(expanded) }
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