package com.example.lusonus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Router
import com.example.lusonus.navigation.LocalStorageList
import com.example.lusonus.data.model.ExternalStorage
import com.example.lusonus.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                // Provides the navController to everything
                val storageList = rememberSaveable {
                    mutableStateListOf<ExternalStorage>(
                        ExternalStorage("test", false),
                        ExternalStorage("test1", false),
                        ExternalStorage("test2", false),
                        ExternalStorage("test3", false),
                    )
                }

                        CompositionLocalProvider(LocalStorageList provides storageList) {
                            CompositionLocalProvider(LocalNavController provides navController) {
                                Router(navController)
                            }
            }
        }
    }
}}