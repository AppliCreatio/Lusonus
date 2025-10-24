package com.example.emptyactivity.screens.SongPlayerScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.organisemedia.Navigation.LocalNavController
import com.example.organisemedia.Navigation.Router
import com.example.organisemedia.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                // Provides the navController to everything
                CompositionLocalProvider(LocalNavController provides navController) {
                    Router(navController)
                }
            }
        }
    }
}