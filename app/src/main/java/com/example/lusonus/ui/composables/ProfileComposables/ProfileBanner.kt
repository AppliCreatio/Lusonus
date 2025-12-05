package com.example.lusonus.ui.composables.ProfileComposables

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ass3_appdev.screens.main.profile_banner.ProfileTextInfo
import com.example.lusonus.R
import java.io.InputStream

/**
 * The Profile Banner appears at the top of the application
 * to display the name and the description of the user. It is a stateful composable,
 * but I could possibly make the profile attributes into a single local provider
 */
@Composable
fun ProfileBanner(
    modifier: Modifier,
    name: String,
    email: String,
    profileImage: Uri,
    editToggle: () -> Unit,
    signOut: () -> Unit,
    delete: () -> Unit
) {

    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .clickable(onClick = editToggle),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = androidx.compose.ui.platform.LocalContext.current
        val bitmap = try {
            val stream: InputStream? = context.contentResolver.openInputStream(profileImage)
            BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            null
        }

        val painter = if (bitmap != null) {
            BitmapPainter(bitmap.asImageBitmap())
        } else {
            painterResource(id = R.drawable.lusonus_placeholder)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painter,
                contentDescription = "This is $name's profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onTertiaryContainer)
            )

            ProfileTextInfo(userName = name, email = email)
        }


        Column {
            ProfileButtons(signOut = signOut, delete = delete)
        }
    }
}

