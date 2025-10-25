package com.example.emptyactivity.Navigation

import android.net.Uri
import com.example.emptyactivity.screens.ProfileScreen.classes.MusicEntryTypes

// Sealed class that holds different routes.
sealed class Routes(val route: String) {
    object Home: Routes(route = "HomeScreenRoute")
    object Media: Routes(route = "MediaScreenRoute")
    object Playlist: Routes(route = "PlaylistScreenRoute")
    object PlaylistDetail : Routes(route = "PlaylistDetailScreenRoute/{playlistName}") {
        fun createRoute(playlistName: String) = "PlaylistDetailScreenRoute/$playlistName"
    }
    object Main : Routes("MainScreenRoute/{name}/{description}/{profileImage}") {
        fun go(name: String, description: String, profileImage: Uri) =
            "MainScreenRoute/$name/$description/${Uri.encode(profileImage.toString())}"
    }

    object AddMusicEntry : Routes("MainScreenRoute/{entryType}") {
        fun go(entryType: MusicEntryTypes) =
            "MainScreenRoute/${entryType.toString()}"
    }

    object Register : Routes("RegisterScreenRoute")
    object FAQ : Routes("FAQScreenRoute")
}