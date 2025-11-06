package com.example.lusonus.ui.screens.MediaScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.theme.AppTheme
import com.example.lusonus.R
import com.example.lusonus.data.model.Media
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.MediaComposables.MediaDetails
import com.example.lusonus.ui.utils.SongQueue


@Composable
fun MediaContent(media: Media, isQueueOpen: Boolean = false) {
    Scaffold(
        bottomBar = {

        }
        ) {
        innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MediaDetails(
                media,
                painterResource(R.drawable.ic_launcher_background)
            )

            if (isQueueOpen)
                SongQueue()
        }

    }
}