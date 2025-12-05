package com.example.lusonus.ui.composables.ProfileComposables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ProfileButtons(signOut: () -> Unit) {
    IconButton(
        onClick = signOut,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = "Delete Account",
            tint = MaterialTheme.colorScheme.onError
        )
    }
}