package com.example.emptyactivity.screens.ProfileScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.emptyactivity.Router
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.ExternalStorage
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.MusicEntry
import com.example.emptyactivity.ui.theme.AppTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
val LocalSongList = compositionLocalOf<MutableList<MusicEntry>> { error("No song list found!") }
val LocalPlaylistList =
    compositionLocalOf<MutableList<MusicEntry>> { error("No playlist list found!") }
val LocalStorageList =
    compositionLocalOf<MutableList<ExternalStorage>> { error("No storage list found!") }

@Composable
fun Profile() {
    AppTheme {
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
                    Scaffold { innerPadding ->
                        Router(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
