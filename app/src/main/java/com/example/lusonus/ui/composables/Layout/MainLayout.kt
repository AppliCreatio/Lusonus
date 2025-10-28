package com.example.lusonus.ui.composables.Layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lusonus.ui.composables.Layout.BottomBar.SharedBottomBar
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar

/**
 * This main layout wraps other composables in a consistent layout. It has the ability to be modular by changing or removing the
 * top bar, bottom bar or floating action button. If nothing is specified, it will default to the default layout
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit,
    screenTitle: String,
    topBar: @Composable (() -> Unit)? = { SharedTopBar(screenTitle) }, // If no specific parameter is passed, set to default top bar
    floatingActionButton: @Composable (() -> Unit)? = null, // this is optional
    bottomBar: @Composable (() -> Unit)? = { SharedBottomBar() } // If no specific parameter is passed, set to default bottom bar
) {
    // This is the main scaffold for our app, we will pass Composables to this method which will
    // then modify the contents of the scaffold as needed.
    Scaffold(
        // Gets the top bar of the scaffold.
        topBar = {
            topBar?.invoke()
        },

        // Gets the bottom bar of the scaffold.
        bottomBar = {
            bottomBar?.invoke()
        },

        // Gets the floating action bar if there is one.
        floatingActionButton = {
            floatingActionButton?.invoke()
        },

        floatingActionButtonPosition = FabPosition.End,

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
