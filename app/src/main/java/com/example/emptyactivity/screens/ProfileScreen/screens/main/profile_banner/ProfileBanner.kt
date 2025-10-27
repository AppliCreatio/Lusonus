package com.example.emptyactivity.screens.ProfileScreen.screens.main.profile_banner

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.emptyactivity.R
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.dialogs.DialogToEditProfile
import com.example.ass3_appdev.screens.main.profile_banner.ProfileTextInfo

/**
 * The Profile Banner appears at the top of the application
 * to display the name and the description of the user. It is a stateful composable,
 * but I could possibly make the profile attributes into a single local provider
 */
@Composable
fun ProfileBanner(modifier: Modifier, name: String, description: String, profileImage: Uri) {

    var profilePicture by rememberSaveable { mutableStateOf(profileImage) }
    var userName by rememberSaveable { mutableStateOf(name) }
    var description by rememberSaveable { mutableStateOf(description) }
    var openEditDialog by rememberSaveable { mutableStateOf(false) }

    // When openEditDialog is true, it will display the edit profile dialog box
    if (openEditDialog)
        DialogToEditProfile(
            { openEditDialog = false },
            { openEditDialog = false },
            userName,
            description,
            { userName = it },
            { description = it },
            { profilePicture = it ?: profilePicture })


    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .clickable(onClick = { openEditDialog = true }),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = if (profilePicture != Uri.EMPTY) {
                profilePicture
            } else R.drawable.resource_default,
            contentDescription = "This is $userName's profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onTertiaryContainer)
        )

        ProfileTextInfo(userName, description)
    }
}

