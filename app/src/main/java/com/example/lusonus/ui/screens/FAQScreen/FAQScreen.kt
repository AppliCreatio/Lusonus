package com.example.lusonus.ui.screens.FAQScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
    val containerDisplay: Modifier =
        Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(10.dp))
    MainLayout({
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            item {
                FAQContent(
                    "About Us",
                    "Lusonus comes from the latin words: Luso, meaning playing; and Sonus, meaning audio. Lusonus itself means playful, which is the energy we bring to the room with our app. Our goal is to make an all purpose media app that is both convenient, and intuitive to use. Our app will be phone based, meaning we are putting your mobile experience first.",
                    modifier =
                        containerDisplay.background(
                            MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(10.dp),
                        ),
                )
            }

            item {
                FAQContent(
                    "How do I remove stuff?",
                    "Hold down on any item and it will change to a trash icon. Tapping the trash icon will remove the item from the app. Holding down on it again returns it to it's normal state.",
                    containerDisplay,
                )
            }
            item {
                FAQContent(
                    "Log Out and Delete Account",
                    "Tap on the lock button on the profile banner to log out and hold the banner to delete.",
                    containerDisplay,
                )
            }
            item {
                FAQContent(
                    "How do I change my storage connection",
                    "You are able to change the storage you are connected to by tapping on the currently connected storage and choosing the new one you want to connect to.",
                    containerDisplay,
                )
            }
            item {
                FAQContent(
                    "Does this app track my information?",
                    "Sort of. This app will only keep track of items added to it, and will track statistics if you are logged in. The app does not however track personal information nor send them to 3rd parties. Everything stays local except your account information.",
                    containerDisplay,
                )
            }

            item {
                FAQContent(
                    "What kind of files are supported?",
                    "As of now, only mp3s and mp4s are supported by Lusonus, but some other media file types may become supported in the future.",
                    containerDisplay,
                )
            }

            item {
                FAQContent(
                    "Is it possible to not have information tracked?",
                    "You can turn off statistics within the settings and even have no profiles.",
                    containerDisplay,
                )
            }
        }
    }, "FAQ")
}
