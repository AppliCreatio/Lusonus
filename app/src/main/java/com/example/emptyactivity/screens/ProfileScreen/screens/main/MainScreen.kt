package com.example.ass3_appdev.screens.main

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.MenuItem
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.MainLayout
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.MinimalDropdownMenu
import com.example.ass3_appdev.screens.main.entries.FavouritePlaylists
import com.example.ass3_appdev.screens.main.entries.MostPlayed
import com.example.emptyactivity.screens.ProfileScreen.screens.main.profile_banner.ProfileBanner


@Composable
fun DisplayProfile(name: String, description: String, profileImage: Uri) {


    // General Container for other information from the profile screen
    val containerDisplay: Modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth()
        .background(
            MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(10.dp)
        )

    MainLayout({
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                ProfileBanner(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .height(120.dp)
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    name, description, profileImage
                )
            }
            item {
                MostPlayed(modifier = containerDisplay.height(220.dp))
            }
            item {
                FavouritePlaylists(modifier = containerDisplay.height(220.dp))
            }
            item {
                ConnectedStorage(
                    modifier = containerDisplay
                        .height(120.dp)
                        .padding(bottom = 10.dp)
                )
            }
        }
    }, {
        val navController = LocalNavController.current
        val faqOption =
            arrayListOf(
                MenuItem("FAQ") { navController.navigate(Routes.FAQ.route) })
        MinimalDropdownMenu(faqOption)
    }, "Home")
}