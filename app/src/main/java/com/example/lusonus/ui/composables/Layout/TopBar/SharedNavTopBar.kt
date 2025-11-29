package com.example.lusonus.ui.composables.Layout.TopBar

import androidx.compose.foundation.layout.Box
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
fun SharedNavTopBar(
    navController: NavHostController = LocalNavController.current
) {
    // Gets the nav stack as a state
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Gets the current screen
    val currentRoute = navBackStackEntry?.destination?.route ?: return

    val sliderItems = listOf(
        "Playlists" to Routes.PlaylistLibrary.route,
        "Media" to Routes.MediaLibrary.route,
        "Folders" to Routes.FolderLibrary.route
    )

    val currentIndex = sliderItems.indexOfFirst { it.second == currentRoute }

    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { sliderItems.size }
    )

    Box(modifier = Modifier.fadeOuterEdge(isToLeft = true, isToRight = true, fadeWidth = 30.dp, color = MaterialTheme.colorScheme.background)){
        BarElementSlider(
            pagerState = pagerState,
            givenSelectedIndex = currentIndex,
            onItemSelected = { index ->
                val targetRoute = sliderItems[index].second
                if (currentRoute != targetRoute) {
                    navController.navigate(targetRoute) {
                        // Preserve state across reselects and avoid piling screens
                        // requires: import androidx.navigation.navOptions
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}