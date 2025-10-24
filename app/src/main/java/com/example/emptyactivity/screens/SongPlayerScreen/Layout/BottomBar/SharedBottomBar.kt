package com.example.organisemedia.Layout.BottomBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.organisemedia.Navigation.LocalNavController

@Composable
fun SharedBottomBar(navController: NavHostController = LocalNavController.current) {
    // Gets the nav stack as a state (so I can have highlighted buttons!)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Gets the current screen
    val currentRoute = navBackStackEntry?.destination?.route

    // Calls the stateless method.
    SharedBottomBarStateless(
        currentRoute = currentRoute,
        onNavigateTo = {
            targetRoute ->
            // If we are already there don't go there...
            if (currentRoute != targetRoute) {
                // Go to route
                navController.navigate(targetRoute) {
                    // Doesn't keep adding the same screens on top each other.
                    launchSingleTop = true
                }
            }
        }
    )
}