package com.example.emptyactivity.screens.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.screens.ProfileScreen.data_classes.MusicEntry
import com.example.emptyactivity.screens.ProfileScreen.AddEntryDialog


/**
 * Parent Container to display the list of songs
 */
@Composable
fun MostPlayed(modifier: Modifier) {

    val title = "My Songs"
    DisplayListOfMusicEntries(title, modifier)

}

/**
 * Parent Container to display the playlists
 */
@Composable
fun FavouritePlaylists(modifier: Modifier) {

    val title = "Favourite Playlists"
    DisplayListOfMusicEntries(title, modifier)

}

/**
 * This composable displays a container that holds any type of music entry (song, playlist, etc) and populates
 * it into a lazy row. It has a permanent Add button within that list that opens a dialog box to add an entry to that list.
 * It holds the the state for the boolean of the dialog box as well as the mutable list of the music entries.
 */
@Composable
fun DisplayListOfMusicEntries(title: String, modifier: Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        var openEntryDialog by rememberSaveable { mutableStateOf(false) }
        val displayList = rememberSaveable { mutableStateListOf<MusicEntry>() }

        Text(
            title, fontSize = 24.sp, modifier = Modifier
                .drawBehind { // Draws a line only at the bottom of the text composable

                    val strokeWidth = 0.5.dp.toPx() * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.White,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
                .fillMaxWidth()
                .padding(3.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(displayList) { item ->
                EntryDisplay(item)
            }

            // Permanent Add button inside the list so it moves to the back as you populate the list
            item {
                Button(
                    onClick = { openEntryDialog = true }, modifier = Modifier
                        .fillMaxHeight()
                        .width(125.dp), shape = RoundedCornerShape(15.dp)
                ) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }


        // if the state is equal to true, open the dialog box
        if (openEntryDialog)
            AddEntryDialog(
                { openEntryDialog = false },
                { openEntryDialog = false },
                { displayList.add(it) })


    }
}

/**
 * This composable is a stateless container that holds all the information for a single music entry
 */
@Composable
fun EntryDisplay(musicEntry: MusicEntry) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .size(125.dp)
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = musicEntry.imageId),
            contentDescription = "This is the cover of the song: ${musicEntry.name} by ${musicEntry.author}.",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(7.dp)).size(90.dp)
        )
        Text(musicEntry.name)
        Text(musicEntry.author, fontSize = 12.sp)
    }
}