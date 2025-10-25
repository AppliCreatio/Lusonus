package com.example.emptyactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.Navigation.LocalNavController
import com.example.emptyactivity.Navigation.Router
import com.example.emptyactivity.Navigation.LocalPlaylistList
import com.example.emptyactivity.Navigation.LocalSongList
import com.example.emptyactivity.Navigation.LocalStorageList
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.ExternalStorage
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.MusicEntry
import com.example.emptyactivity.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                // Provides the navController to everything
                val songList = rememberSaveable { mutableStateListOf<MusicEntry>() }
                val playlistList = rememberSaveable { mutableStateListOf<MusicEntry>() }
                val storageList = rememberSaveable {
                    mutableStateListOf<ExternalStorage>(
                        ExternalStorage("test", false),
                        ExternalStorage("test1", false),
                        ExternalStorage("test2", false),
                        ExternalStorage("test3", false),
                    )
                }
                CompositionLocalProvider(LocalSongList provides songList) {
                    CompositionLocalProvider(LocalPlaylistList provides playlistList) {
                        CompositionLocalProvider(LocalStorageList provides storageList) {
                            CompositionLocalProvider(LocalNavController provides navController) {
                                Router(navController)
                            }
            }
        }
    }
}}}}