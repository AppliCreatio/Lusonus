package com.example.lusonus

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.lusonus.data.model.rememberExternalStorageList
import com.example.lusonus.data.sharedinstances.Global
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.LocalStorageList
import com.example.lusonus.navigation.Router
import com.example.lusonus.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                val globals by remember { mutableStateOf<Global>(Global) }
                // Provides the navController to everything
                val storageList = rememberExternalStorageList()

                CompositionLocalProvider(LocalStorageList provides storageList) {
                    CompositionLocalProvider(LocalGlobals provides globals) {
                        CompositionLocalProvider(LocalNavController provides navController) {
                            Router(navController)
                        }
                    }

                }
            }
        }
    }
}