package com.example.lusonus.ui.composables.Layout.BottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.organisemedia.Helper.Style.IconTint
import com.example.lusonus.navigation.Routes

// Statless method for shared bottom bar
@Composable
fun SharedBottomBarStateless(
    currentRoute: String?,
    onNavigateTo: (String) -> Unit
) {
    // The bottom bar of the scaffold.
    BottomAppBar(
        // The colors of the bottom bar.
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        // I'm not using the actions parameter since I want custom spacing.
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            // Gets the color scheme used.
            val colorScheme = MaterialTheme.colorScheme

            // The navigation button to go to the Playlist Screen.
            IconButton(
                onClick = {
                    onNavigateTo(Routes.PlaylistLibrary.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Go to Playlist Screen.",
                    tint = IconTint(currentRoute, Routes.PlaylistLibrary.route, colorScheme)
                )
            }

            // The navigation button to go to the Home Screen.
//            IconButton(
//                onClick = {
//                    onNavigateTo(Routes.Home.route)
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Home,
//                    contentDescription = "Go to Home Screen.",
//                    tint = IconTint(currentRoute, Routes.Home.route, colorScheme)
//                )
//            }

            // The navigation button to go to the Song Screen.
            IconButton(
                onClick = {
                    onNavigateTo(Routes.Media.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Go to Media Screen.",
                    tint = IconTint(currentRoute, Routes.Media.route, colorScheme)
                )
            }
        }
    }
}