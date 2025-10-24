package com.example.organisemedia.Navigation

// Sealed class that holds different routes.
sealed class Routes(val route: String) {
    object Home: Routes(route = "HomeScreenRoute")
    object Media: Routes(route = "MediaScreenRoute")
    object Playlist: Routes(route = "PlaylistScreenRoute")
    object PlaylistDetail : Routes(route = "PlaylistDetailScreenRoute/{playlistName}") {
        fun createRoute(playlistName: String) = "PlaylistDetailScreenRoute/$playlistName"
    }
}