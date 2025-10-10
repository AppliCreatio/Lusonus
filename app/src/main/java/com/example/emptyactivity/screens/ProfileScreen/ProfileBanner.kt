package com.example.emptyactivity.screens.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.R
import com.example.emptyactivity.screens.ProfileScreen.DialogToEditProfile


/**
 * The Profile Banner appears at the top of the application
 * to display the name and the description of the user. It is a stateful composable
 */
@Composable
fun ProfileBanner(modifier: Modifier) {

    var imageId by rememberSaveable { mutableIntStateOf(R.drawable.resource_default) }
    var userName by rememberSaveable { mutableStateOf("[Click Me]") }
    var description by rememberSaveable { mutableStateOf("Hello there!") }
    var openEditDialog by rememberSaveable { mutableStateOf(false) }

    // for the future
    // var playlistCount by rememberSaveable { mutableIntStateOf(0) }
    // var songCount by rememberSaveable { mutableIntStateOf(0) }

    // When openEditDialog is true, it will display the edit profile dialog box
    if (openEditDialog)
        DialogToEditProfile(
            { openEditDialog = false },
            { openEditDialog = false },
            { userName = it },
            { description = it })


    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .clickable(onClick = { openEditDialog = true }),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = imageId),
            contentDescription = "This is $userName's profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        ProfileTextInfo(userName, description)
    }
}

/**
 * A stateless composable that holds all of the text information of the profile and displays it
 */
@Composable
fun ProfileTextInfo(userName: String, description: String) {

    Column {
        Text(userName, fontSize = 24.sp)
        Text(description, fontSize = 17.sp)

    }
}