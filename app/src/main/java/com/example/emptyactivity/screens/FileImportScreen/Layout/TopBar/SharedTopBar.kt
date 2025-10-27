package com.example.emptyactivity.screens.FileImportScreen.Layout.TopBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.emptyactivity.navigation.LocalNavController

@Composable
fun SharedTopBar(
    screenTitle: String,
    navController: NavHostController = LocalNavController.current
) {
    // Gets whether you can go back a screen.
    val canNavigateBack = navController.previousBackStackEntry != null

    // Calls top bar stateless.
    SharedTopBarStateless(
        screenTitle = screenTitle,
        canNavigateBack = canNavigateBack,
        onNavigateBack = {
            if (canNavigateBack) navController.navigateUp()
        }
    )
}
