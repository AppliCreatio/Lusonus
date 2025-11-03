package com.example.lusonus.ui.composables.Layout.BottomBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun MediaBottomBar(isQueueOpen: Boolean) {
    // TODO: Place boolean parameter functionalities into dedicated ViewModel class.

    var isLiked by rememberSaveable { mutableStateOf(false) }
    var isPaused by rememberSaveable { mutableStateOf(false) }

    MediaBottomBarStateless(isQueueOpen, isLiked, isPaused)
}