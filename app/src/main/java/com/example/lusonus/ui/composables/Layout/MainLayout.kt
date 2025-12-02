package com.example.lusonus.ui.composables.Layout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.BottomBar.SharedBottomBar
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar

/**
 * This main layout wraps other composables in a consistent layout. It has the ability to be modular by changing or removing the
 * top bar, bottom bar or floating action button. If nothing is specified, it will default to the default layout
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit,
    screenTitle: String,
    topbarAddButton: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null, // this is optional
    topBar: @Composable (() -> Unit)? = { SharedTopBar(screenTitle, topbarAddButton) },// If no specific parameter is passed, set to default top bar
    bottomBar: @Composable (() -> Unit)? = { SharedBottomBar() }, // this is optional
    snackbarHost: @Composable () -> Unit = {} // this is optional, mostly used for authentication
) {


    val navController = LocalNavController.current
    val globals = LocalGlobals.current
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: return

    val screensWithPlayer = listOf(Routes.MediaLibrary.route, Routes.FolderLibrary.route, Routes.PlaylistLibrary.route,
        Routes.Playlist.route)
    val showPopUp = currentRoute in screensWithPlayer
    // This is the main scaffold for our app, we will pass Composables to this method which will
    // then modify the contents of the scaffold as needed.
    Scaffold(
        // Gets the top bar of the scaffold.
        topBar = {
            topBar?.invoke()
        },

        // Gets the bottom bar of the scaffold.
        bottomBar = {
            Column {
                bottomBar?.invoke()
            }
        },

        // Gets the floating action bar if there is one.
        floatingActionButton = {
            floatingActionButton?.invoke()
        },

        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = snackbarHost,

        containerColor = MaterialTheme.colorScheme.background
    ) {
        paddingValues ->
        // This is where the passed Composable is called.
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ){
            content()
        }
    }
}
