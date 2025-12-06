package com.example.lusonus.ui.composables.RegisterScreenComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterBanner() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.TwoTone.AccountBox,
            contentDescription = "Register Icon"
        )
        Text(text = "Lusonus awaits", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}