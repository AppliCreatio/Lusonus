package com.example.assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment_2.ui.theme.Assignment_2Theme
import java.util.Queue


@Composable
fun MainApp(modifier: Modifier = Modifier) {
    var isQueueOpen by rememberSaveable { mutableStateOf(false) }
    var isLiked by rememberSaveable { mutableStateOf(false) }
    var isPaused by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,

        topBar = {Unit},
        bottomBar = {
            BottomAppBar (
                actions = {
                    Row (
                        modifier = modifier
                            .padding(20.dp, 10.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            modifier = modifier,
                            onClick = { isQueueOpen = !isQueueOpen }
                        ) {
                            Icon(
                                modifier = modifier.size(30.dp),
                                painter = painterResource(R.drawable.queue_button),
                                contentDescription = "A button to open and close the song queue."
                            )
                        }

                        IconButton(
                            onClick = {Unit},
                            modifier = modifier,
                            enabled = false) {
                            Icon(
                                modifier = modifier.size(30.dp),
                                painter = painterResource(R.drawable.previous_button),
                                contentDescription = "A button to skip to the previous song."
                            )
                        }

                        IconButton(
                            modifier = modifier,
                            onClick = { isPaused = !isPaused }
                        ) {
                            Icon(
                                modifier = modifier.size(30.dp),
                                painter = if (isPaused) painterResource(R.drawable.pause_button) else painterResource(R.drawable.play_button),
                                contentDescription = "A button to play and pause the current song."
                            )
                        }

                        IconButton(
                            onClick = {Unit},
                            modifier = modifier,
                            enabled = false) {
                            Icon(
                                modifier = modifier.size(30.dp),
                                painter = painterResource(R.drawable.next_button),
                                contentDescription = "A button to skip to the next song.",
                            )
                        }

                        IconButton(
                            modifier = modifier,
                            onClick = { isLiked = !isLiked }
                        ) {
                            Icon(
                                modifier = modifier.size(30.dp),
                                painter = if (isLiked) painterResource(R.drawable.heart_clicked) else painterResource(R.drawable.heart_empty),
                                contentDescription = "A heart-shaped button to favorite the current song."
                            )
                        }
                    }
                }
            )
        }
        ) { innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SongDetails(
                modifier = modifier,
                "Crazy Train",
                "Ozzy Osbourne",
                painterResource(R.drawable.ic_launcher_background)
            )

            if (isQueueOpen)
                SongQueue()
        }
    }
}

@Preview
@Composable
fun MainAppPreview () {
    Assignment_2Theme {
        MainApp()
    }
}