package com.example.lusonus.navigation

import android.net.Uri

// Sealed class that holds different routes.
sealed class Routes(val route: String) {
    object MediaLibrary: Routes(route = "MediaLibraryScreenRoute")
    object MediaPlayer: Routes(route = "MediaScreenRoute") {
//        fun go(name: String) =
//            "MediaScreenRoute/$name"
    }
    object PlaylistLibrary: Routes(route = "PlaylistLibraryScreenRoute")
    object Playlist : Routes(route = "PlaylistScreenRoute/{playlistName}") {
        fun createRoute(playlistName: String) = "PlaylistScreenRoute/$playlistName"
    }
    object Profile : Routes("ProfileScreenRoute") {
        fun go(name: String, description: String, profileImage: Uri) =
            "ProfileScreenRoute/$name/$description/${Uri.encode(profileImage.toString())}"
    }

    //TODO: delete eventually
//    object AddMusicEntry : Routes("MainScreenRoute/{entryType}") {
//        fun go(entryType: MusicEntryTypes) =
//            "MainScreenRoute/${entryType.toString()}"
//    }

    object Register : Routes("RegisterScreenRoute")
    object FAQ : Routes("FAQScreenRoute")
    object Settings : Routes("FolderScreenRoute")
    object Folders : Routes("FoldersViewScreenRoute")
}