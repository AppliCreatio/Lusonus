package com.example.organisemedia.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.organisemedia.Screens.Home.HomeScreen
import com.example.organisemedia.Screens.Media.MediaScreen
import com.example.organisemedia.Screens.Playlist.PlaylistScreen
import com.example.organisemedia.Screens.PlaylistDetail.PlaylistDetailScreen
import com.example.organisemedia.ViewModels.MediaViewModel
import com.example.organisemedia.ViewModels.PlaylistViewModel

// Allows the passing down of data. (Provider pattern)
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }

@Composable
// Router deals with managing our different pages and which route calls which composable.
fun Router(navController: NavHostController, modifier: Modifier = Modifier) {
    // These are the links between routes and the composables.
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route, // the starting screen.
        modifier = modifier.fillMaxSize(),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        // Home screen route.
        composable(route = Routes.Home.route) {
            HomeScreen()
        }

        // Media screen route.
        composable(route = Routes.Media.route) {
            MediaScreen()
        }

        // Playlist screen route.
        composable(route = Routes.Playlist.route) {
            PlaylistScreen()
        }

        // Playlist details screen
        composable(
            route = Routes.PlaylistDetail.route,
            arguments = listOf(navArgument(name = "playlistName") { type = NavType.StringType })
        ) {
            backStackEntry ->

            // Gets parameter from the URL.
            val playlistName = backStackEntry.arguments?.getString("playlistName") ?: ""

            // Gets the view models
            val playlistViewModel: PlaylistViewModel = viewModel(viewModelStoreOwner =LocalNavController.current.context as ComponentActivity)
            val mediaViewModel: MediaViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity)

            PlaylistDetailScreen(
                playlistName = playlistName,
                playlistViewModel = playlistViewModel,
                mediaViewModel = mediaViewModel
            )
        }
    }
}