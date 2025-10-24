package com.example.organisemedia.Layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.organisemedia.Layout.BottomBar.SharedBottomBar
import com.example.organisemedia.Layout.TopBar.SharedTopBar
import com.example.organisemedia.Navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit,
    screenTitle: String,
    floatingActionButton: @Composable (() -> Unit)? = null // this is optional
) {
    // This is the main scaffold for our app, we will pass Composables to this method which will
    // then modify the contents of the scaffold as needed.
    Scaffold(
        // Gets the top bar of the scaffold.
        topBar = {
            SharedTopBar(screenTitle)
        },

        // Gets the bottom bar of the scaffold.
        bottomBar = {
            SharedBottomBar()
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
