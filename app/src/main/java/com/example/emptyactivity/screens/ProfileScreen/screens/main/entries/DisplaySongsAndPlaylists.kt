package com.example.ass3_appdev.screens.main.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.LocalPlaylistList
import com.example.emptyactivity.navigation.LocalSongList
import com.example.emptyactivity.navigation.Routes
import com.example.emptyactivity.screens.ProfileScreen.classes.MusicEntryTypes
import com.example.emptyactivity.screens.ProfileScreen.screens.main.entries.EntryDisplay


/**
 * Parent Container to display the list of songs
 */
@Composable
fun MostPlayed(modifier: Modifier) {

    val title = "My Songs"
    DisplayListOfMusicEntries(title, MusicEntryTypes.SONG, modifier)

}

/**
 * Parent Container to display the playlists
 */
@Composable
fun FavouritePlaylists(modifier: Modifier) {

    val title = "Favourite Playlists"
    DisplayListOfMusicEntries(title, MusicEntryTypes.PLAYLISTS, modifier)

}

/**
 * This composable displays a container that holds any type of music entry (song, playlist, etc) and populates
 * it into a lazy row. It has a permanent Add button within that list that opens a dialog box to add an entry to that list.
 * It holds the the state for the boolean of the dialog box as well as the mutable list of the music entries.
 */
@Composable
fun DisplayListOfMusicEntries(
    title: String,
    musicEntryType: MusicEntryTypes,
    modifier: Modifier
) {

    // gets the nav controller
    val navController = LocalNavController.current

    // this gets the appropriate local list depending on which type is requested within the method parameters
    val displayList = when (musicEntryType) {
        MusicEntryTypes.SONG -> LocalSongList.current
        MusicEntryTypes.PLAYLISTS -> LocalPlaylistList.current
    }

    Column(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

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

            // displays all the entries of the local list
            items(displayList) { item ->
                EntryDisplay(item)
            }

            // Permanent Add button inside the list so it moves to the back as you populate the list
            item {
                Button(
                    onClick = { navController.navigate(Routes.AddMusicEntry.go(musicEntryType)) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(125.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }
    }
}



