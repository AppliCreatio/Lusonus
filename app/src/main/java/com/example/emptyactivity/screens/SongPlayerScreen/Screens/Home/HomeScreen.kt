package com.example.organisemedia.Screens.Home

import androidx.compose.runtime.Composable
import com.example.organisemedia.Layout.MainLayout
import com.example.organisemedia.Navigation.LocalNavController
import com.example.organisemedia.Screens.Home.Content.WelcomeContent

@Composable
// The Home Screen. This is the startup screen of the app.
fun HomeScreen() {
    // Calls the main layout.
    MainLayout(
        content = { HomeContent() },
        screenTitle = "Home"
    )
}