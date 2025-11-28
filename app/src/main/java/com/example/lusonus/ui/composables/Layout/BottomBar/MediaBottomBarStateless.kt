package com.example.lusonus.ui.composables.Layout.BottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lusonus.R

@Composable
fun MediaBottomBarStateless(
    isLiked: Boolean,
    isPaused: Boolean,
    onToggleQueue: () -> Unit,
    onToggleLike: () -> Unit,
    onTogglePause: () -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            Row(
                modifier = Modifier
                    .padding(20.dp, 10.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onToggleQueue) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.queue_button),
                        contentDescription = "Toggle queue"
                    )
                }

                IconButton(onClick = onTogglePause) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = if (isPaused)
                            painterResource(R.drawable.pause_button)
                        else
                            painterResource(R.drawable.play_button),
                        contentDescription = "Play/pause"
                    )
                }

                IconButton(onClick = onToggleLike) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = if (isLiked)
                            painterResource(R.drawable.heart_clicked)
                        else
                            painterResource(R.drawable.heart_empty),
                        contentDescription = "Toggle favorite"
                    )
                }
            }
        }
    )
}

