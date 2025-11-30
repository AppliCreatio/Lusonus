package com.example.lusonus.ui.composables.Layout.TopBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.BarElementSlider.BarElementSlider
import com.example.lusonus.ui.utils.fadeOuterEdge

@Composable
fun SharedTopBar(
    pageName: String,
    actionButton: @Composable (() -> Unit)? = null,
    navController: NavHostController = LocalNavController.current,
) {
        // Gets whether you can go back a screen.
        var canNavigateBack = navController.previousBackStackEntry != null
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: return

        val sliderItems = listOf(
            "Playlists" to Routes.PlaylistLibrary.route,
            "Media" to Routes.MediaLibrary.route,
            "Folders" to Routes.FolderLibrary.route
        )

        if (sliderItems.any { it.second == currentRoute})
        {
            canNavigateBack = false
        }

        // Calls top bar stateless.
        SharedTopBarStateless(
            pageName,
            canNavigateBack = canNavigateBack,
            onNavigateBack = {
                if (canNavigateBack) navController.navigateUp()
            },
            actionButton = actionButton
        )
}
