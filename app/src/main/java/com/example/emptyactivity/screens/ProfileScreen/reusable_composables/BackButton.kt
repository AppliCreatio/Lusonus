package com.example.emptyactivity.screens.ProfileScreen.reusable_composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.emptyactivity.navigation.LocalNavController

/**
 * A reusable back button
 */
@Composable
fun BackButton() {
    val navController = LocalNavController.current

    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")
    }
}