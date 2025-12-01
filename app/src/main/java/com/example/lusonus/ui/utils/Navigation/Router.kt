package com.example.lusonus.navigation

import FolderLibraryScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ass3_appdev.screens.main.ProfileScreen
import com.example.lusonus.data.model.ExternalStorage
import com.example.lusonus.data.model.singletons.Global
import com.example.lusonus.ui.screens.FAQScreen.FAQScreen
import com.example.lusonus.ui.screens.FolderScreen.FolderScreen
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaLibraryScreen
import com.example.lusonus.ui.screens.MediaScreen.MediaScreen
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryScreen
import com.example.lusonus.ui.screens.PlaylistScreen.PlaylistScreen
import com.example.lusonus.ui.screens.RegisterScreen.RegisterScreen
import com.example.lusonus.ui.screens.SettingsScreen.SettingScreen


// Allows the passing down of data. (Provider pattern)
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
val LocalStorageList =
    compositionLocalOf<MutableList<ExternalStorage>> { error("No storage list found!") }

val LocalGlobals =
    compositionLocalOf<Global> { error("No media list found!") }
@RequiresApi(Build.VERSION_CODES.O)
@Composable
// Router deals with managing our different pages and which route calls which composable.
fun Router(navController: NavHostController, modifier: Modifier = Modifier) {
    // These are the links between routes and the composables.
    NavHost(
        navController = navController,
        startDestination = Routes.PlaylistLibrary.route, // the starting screen.
        modifier = modifier.fillMaxSize(),
    ) {
        // Media screen route.
        composable(route = Routes.MediaLibrary.route) {
            MediaLibraryScreen()
        }

        // Media Player route
        composable(
            route = Routes.MediaPlayer.route,
            arguments = listOf(navArgument(name = "mediaName") { type = NavType.StringType })
        ) {
            backStackEntry ->

            // Gets parameter from the URL.
            val mediaName = backStackEntry.arguments?.getString("mediaName") ?: ""

            MediaScreen(mediaName = mediaName)
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
            ProfileScreen()
        }

        // Register screen route.
        // TODO: Make view model for register.
        composable(Routes.Register.route) { RegisterScreen() }

        composable(Routes.Settings.route) { SettingScreen() }

        composable(Routes.FolderLibrary.route) { FolderLibraryScreen() }
        composable(
            route = Routes.Folder.route,
            arguments = listOf(navArgument(name = "folderName") { type = NavType.StringType })
        ) {
                backStackEntry ->

            // Gets parameter from the URL.
            val folderName = backStackEntry.arguments?.getString("folderName") ?: ""
            FolderScreen(
                folderName = folderName,
            )
        }
        composable(Routes.FAQ.route) { FAQScreen() }
    }
}