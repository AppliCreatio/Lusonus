package com.example.lusonus.navigation

import android.net.Uri

// Sealed class that holds different routes.
sealed class Routes(
    val route: String,
) {
    object MediaLibrary : Routes(route = "MediaLibraryScreenRoute")

    object MediaPlayer : Routes(route = "MediaScreenRoute/{mediaName}") {
        fun go(mediaName: String) = "MediaScreenRoute/$mediaName"
    }

    object PlaylistLibrary : Routes(route = "PlaylistLibraryScreenRoute")

    object Playlist : Routes(route = "PlaylistScreenRoute/{playlistName}") {
        fun createRoute(playlistName: String) = "PlaylistScreenRoute/$playlistName"
    }

    object Profile : Routes("ProfileScreenRoute") {
        fun go(
            name: String,
            description: String,
            profileImage: Uri,
        ) = "ProfileScreenRoute/$name/$description/${Uri.encode(profileImage.toString())}"
    }

    object Register : Routes("RegisterScreenRoute")

    object FAQ : Routes("FAQScreenRoute")

    object Settings : Routes("FolderScreenRoute")

    object FolderLibrary : Routes("FoldersLibraryScreenRoute")

    object Folder : Routes(route = "FolderScreenRoute/{folderName}") {
        fun createRoute(folderName: String) = "FolderScreenRoute/$folderName"
    }
}
