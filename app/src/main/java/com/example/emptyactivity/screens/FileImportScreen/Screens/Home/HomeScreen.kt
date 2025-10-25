package com.example.emptyactivity.screens.FileImportScreen.Screens.Home

import androidx.compose.runtime.Composable
import com.example.emptyactivity.screens.FileImportScreen.Layout.MainLayout

@Composable
// The Home Screen. This is the startup screen of the app.
fun HomeScreen() {
    // Calls the main layout.
    MainLayout(
        content = { HomeContent() },
        screenTitle = "Home"
    )
}