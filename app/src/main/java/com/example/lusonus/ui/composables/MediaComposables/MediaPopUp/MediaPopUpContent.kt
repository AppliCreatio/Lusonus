package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lusonus.R
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaPopUpControls
import com.example.lusonus.ui.screens.MediaLibraryScreen.MediaPopUpInfo

@Composable
fun MediaPopUpContent (mediaName: String = "Media Name", mediaArtist: String = "Media Artist", isPaused: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth(), // Optional: Makes the Box fill the available space
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Set the background color of the card
            contentColor = MaterialTheme.colorScheme.primary // Set the color of the content inside the card
        ),
    ) {
        Row (
            modifier = Modifier
                .padding(20.dp, 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            MediaPopUpInfo(mediaName, mediaArtist)

            Row {
                MediaPopUpControls(isPaused)
            }
        }
    }
}

@Preview
@Composable
fun MediaPopUpPreview () {
    MaterialTheme {
        MediaPopUpContent()
    }
}