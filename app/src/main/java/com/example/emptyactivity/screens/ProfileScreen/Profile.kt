package com.example.emptyactivity.screens.ProfileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.screens.ProfileScreen.ConnectedStorage
import com.example.emptyactivity.screens.ProfileScreen.FavouritePlaylists
import com.example.emptyactivity.screens.ProfileScreen.MostPlayed
import com.example.emptyactivity.screens.ProfileScreen.ProfileBanner

@Composable
fun DisplayProfile(modifier: Modifier) {

    // General Container for other information from the pofile screen
    val containerDisplay: Modifier = modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(10.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 18.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {

        item {
            ProfileBanner(
                modifier = modifier
                    .padding(horizontal = 10.dp)
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }
        item {
            MostPlayed(modifier = containerDisplay.height(220.dp))
        }
        item {
            FavouritePlaylists(modifier = containerDisplay.height(220.dp))
        }
        item {
            ConnectedStorage(modifier = containerDisplay.height(120.dp))
        }
    }
}