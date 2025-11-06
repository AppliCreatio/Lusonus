package com.example.lusonus.ui.screens.FAQScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A FAQ container that displays info about a FAQ.
 */
@Composable
fun FAQContent(title: String, content: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Text(
            content,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
    }
}