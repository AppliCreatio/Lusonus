package com.example.lusonus.ui.composables.Layout.TopBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return

    val sliderItems = listOf(
        "Playlists" to Routes.PlaylistLibrary.route,
        "Media" to Routes.MediaLibrary.route,
        "Folders" to Routes.FolderLibrary.route
    )

    // Determine the current index for the slider.
    val currentIndex = sliderItems.indexOfFirst { it.second == currentRoute }.coerceAtLeast(0)

    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { sliderItems.size }
    )

    Box(
        modifier = Modifier.fadeOuterEdge(
            isToLeft = true,
            isToRight = true,
            fadeWidth = 30.dp,
            color = MaterialTheme.colorScheme.background
        )
    ) {
        BarElementSlider(
            pagerState = pagerState,
            givenSelectedIndex = currentIndex,
            onItemSelected = { index ->
                val targetRoute = sliderItems[index].second
                if (currentRoute != targetRoute) {
                    navController.navigate(targetRoute) {
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

    // Ensure the pager updates if the route changes externally.
    LaunchedEffect(currentRoute) {
        val index = sliderItems.indexOfFirst { it.second == currentRoute }.coerceAtLeast(0)
        if (index != pagerState.currentPage) {
            pagerState.scrollToPage(index)
        }
    }
}