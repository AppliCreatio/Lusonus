package com.example.lusonus.ui.screens.SettingsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.lusonus.ui.composables.Layout.MainLayout

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen(){
    MainLayout({

    }, "Settings")
}