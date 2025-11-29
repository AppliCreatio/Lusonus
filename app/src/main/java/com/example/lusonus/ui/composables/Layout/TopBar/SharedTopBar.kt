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
    navController: NavHostController = LocalNavController.current,
) {
        // Gets whether you can go back a screen.
        val canNavigateBack = navController.previousBackStackEntry != null

        // Calls top bar stateless.
        SharedTopBarStateless(
            pageName,
            canNavigateBack = canNavigateBack,
            onNavigateBack = {
                if (canNavigateBack) navController.navigateUp()
            }
        )
}
