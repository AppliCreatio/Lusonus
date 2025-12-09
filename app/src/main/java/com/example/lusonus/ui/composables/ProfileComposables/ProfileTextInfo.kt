package com.example.ass3_appdev.screens.main.profile_banner

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

/*
*   Alex made this entire file
*  */

/**
 * A stateless composable that holds all of the text information of the profile and displays it
 */
@Composable
fun ProfileTextInfo(
    userName: String,
    email: String,
) {
    Column {
        Text(userName, fontSize = 24.sp)
        Text(email, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}
