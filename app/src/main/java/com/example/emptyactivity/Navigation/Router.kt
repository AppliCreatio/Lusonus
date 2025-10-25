package com.example.emptyactivity.Navigation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.emptyactivity.screens.ProfileScreen.screens.add_music_entries.AddMusicEntryScreen
import com.example.emptyactivity.screens.ProfileScreen.screens.faq.FAQScreen
import com.example.ass3_appdev.screens.main.DisplayProfile
import com.example.emptyactivity.screens.ProfileScreen.screens.register.RegisterScreen
import com.example.emptyactivity.screens.FileImportScreen.Screens.Home.HomeScreen
import com.example.emptyactivity.screens.FileImportScreen.Screens.Media.MediaScreen
import com.example.emptyactivity.screens.FileImportScreen.Screens.Playlist.PlaylistScreen
import com.example.emptyactivity.screens.FileImportScreen.Screens.PlaylistDetail.PlaylistDetailScreen
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.ExternalStorage
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.MusicEntry
import com.example.organisemedia.ViewModels.MediaViewModel
import com.example.organisemedia.ViewModels.PlaylistViewModel

// Allows the passing down of data. (Provider pattern)
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
val LocalSongList = compositionLocalOf<MutableList<MusicEntry>> { error("No song list found!") }
val LocalPlaylistList =
    compositionLocalOf<MutableList<MusicEntry>> { error("No playlist list found!") }
val LocalStorageList =
    compositionLocalOf<MutableList<ExternalStorage>> { error("No storage list found!") }
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
        composable(Routes.Main.route) {
            DisplayProfile(
                it.arguments?.getString("name") ?: "",
                it.arguments?.getString("description") ?: "",
                it.arguments?.getString("profileImage")?.let { Uri.decode(it).toUri() }
                    ?: Uri.EMPTY,
            )
        }
        composable(Routes.Register.route) { RegisterScreen() }
        composable(Routes.FAQ.route) { FAQScreen() }
        composable(Routes.AddMusicEntry.route) {
            AddMusicEntryScreen(
                it.arguments?.getString("entryType") ?: ""
            )
        }
    }
}