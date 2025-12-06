package com.example.lusonus.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.lusonus.ui.theme.AppTheme

@Composable
fun <T : Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> =
    rememberSaveable(
        saver =
            listSaver(
                save = { stateList ->
                    if (stateList.isNotEmpty()) {
                        val first = stateList.first()
                        if (!canBeSaved(first)) {
                            throw IllegalStateException(
                                "${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.",
                            )
                        }
                    }
                    stateList.toList()
                },
                restore = { it.toMutableStateList() },
            ),
    ) {
        elements.toList().toMutableStateList()
    }

@Composable
fun SongQueue(modifier: Modifier = Modifier) {
    val queue = rememberMutableStateListOf<String>()
    var newSong by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Your Queue", textAlign = TextAlign.Center)

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = newSong,
                onValueChange = {
                    newSong = it
                },
                modifier = modifier,
                placeholder = { Text(text = "Enter a Song Name") },
                maxLines = 1,
                singleLine = true,
            )

            Button(
                onClick = {
                    if (newSong.isNotEmpty()) {
                        queue.add(newSong)
                    }
                },
            ) {
                Text(
                    text = "Add Song",
                    textAlign = TextAlign.Center,
                )
            }
        }

        LazyColumn(modifier = modifier) {
            if (queue.isNotEmpty()) {
                itemsIndexed(queue) { index, item ->
                    Text(text = "${index + 1}. $item")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongQueuePreview() {
    AppTheme {
        SongQueue()
    }
}
