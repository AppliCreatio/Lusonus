package com.example.organisemedia.Layout.TopBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBarStateless(
    screenTitle: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit
) {
    // The top bar of the scaffold.
    CenterAlignedTopAppBar(
        // The colors of the top bar.
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),

        // The title of the top bar.
        title = {
            Text(
                text = screenTitle,
                style = MaterialTheme.typography.titleLarge
            )
        },

        // The navigation icon of the top bar, "go back" button.
        navigationIcon = {
            // Hides the button if there is nothing to go back to.
            if (canNavigateBack) {
                IconButton(
                    onClick = {
                        onNavigateBack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back."
                    )
                }
            }
        },

        // The options menu (if needed)
//        actions = {
//            IconButton(onClick = { /* do something */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = "Menu"
//                )
//            }
//        },
    )
}