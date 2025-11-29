package com.example.lusonus.ui.screens.FAQScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.composables.Layout.MainLayout

/**
 * A composable which acts as the screen for the about/FAQ page. It shows all Frequently Asked Questions about the app.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FAQScreen() {

    val containerDisplay: Modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(10.dp))
    MainLayout({
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                FAQContent(
                    "About Us",
                    "Lusonus comes from the latin words: Luso, meaning playing; and Sonus, meaning audio. Lusonus itself means playful, which is the energy we bring to the room with our app. Our goal is to make an all purpose media app that is both convienient, and intuitive to use. Our app will be phone based, meaning we are giving you our best to make your mobile media feel like it belongs.",
                    modifier = containerDisplay.background(
                        MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    ),
                )
            }

            item {
                FAQContent(
                    "Delete Music Entries",
                    "Tap on a song to prompt a dialog to confirm deletion",
                    containerDisplay
                )
            }
            item {
                FAQContent(
                    "Log Out",
                    "Tap on the floating bottom on the bottom right to prompt log out dialog",
                    containerDisplay
                )
            }
            item {
                FAQContent(
                    "How do I change my storage connection",
                    "You are able to change the storage you are connected to by tapping on the currently connected storage and choosing the new one you want to connect to.",
                    containerDisplay
                )
            }
            item {
                FAQContent(
                    "Does this app track my information",
                    "At this moment: No. I did not learn how to track info yet",
                    containerDisplay
                )
            }
        }

    }, "FAQ")

}