package com.example.lusonus.navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lusonus.ui.screens.FAQScreen.FAQScreen
import com.example.ass3_appdev.screens.main.DisplayProfile
import com.example.lusonus.ui.screens.RegisterScreen.RegisterScreen
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryScreen
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryScreen
import com.example.lusonus.ui.screens.PlaylistScreen.PlaylistScreen
import com.example.lusonus.data.model.ExternalStorage
import com.example.lusonus.ui.screens.FolderViewScreen.FolderViewScreen
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryViewModel
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel
import com.example.lusonus.ui.screens.SettingsScreen.SettingScreen

// Allows the passing down of data. (Provider pattern)
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
val LocalStorageList =
    compositionLocalOf<MutableList<ExternalStorage>> { error("No storage list found!") }
@Composable
// Router deals with managing our different pages and which route calls which composable.
fun Router(navController: NavHostController, modifier: Modifier = Modifier) {
    // These are the links between routes and the composables.
    NavHost(
        navController = navController,
        startDestination = Routes.PlaylistLibrary.route, // the starting screen.
        modifier = modifier.fillMaxSize(),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        // Media screen route.
        composable(route = Routes.MediaLibrary.route) {
            MediaLibraryScreen()
        }

        // PlaylistLibrary screen route.
        composable(route = Routes.PlaylistLibrary.route) {
            PlaylistLibraryScreen()
        }

        // Playlist screen route.
        composable(
            route = Routes.Playlist.route,
            arguments = listOf(navArgument(name = "playlistName") { type = NavType.StringType })
        ) {
            backStackEntry ->

            // Gets parameter from the URL.
            val playlistName = backStackEntry.arguments?.getString("playlistName") ?: ""

            PlaylistScreen(
                playlistName = playlistName,
            )
        }

        // Profile screen route.
        // TODO: Make view model for profile.
        composable(Routes.Profile.route) {
            DisplayProfile()
        }

        // Register screen route.
        // TODO: Make view model for register.
        composable(Routes.Register.route) { RegisterScreen() }

        composable(Routes.Settings.route) { SettingScreen() }

        composable(Routes.Folders.route) { FolderViewScreen() }

        composable(Routes.FAQ.route) { FAQScreen() }
    }
}