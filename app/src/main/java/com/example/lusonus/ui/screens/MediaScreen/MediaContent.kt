package com.example.lusonus.ui.screens.MediaScreen


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.theme.AppTheme
import com.example.lusonus.R
import com.example.lusonus.data.model.Media
import com.example.lusonus.services.ACTION_PLAY_URI
import com.example.lusonus.services.EXTRA_URI
import com.example.lusonus.services.PlayerService
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.MediaComposables.MediaDetails
import com.example.lusonus.ui.utils.SongQueue
import com.example.lusonus.ui.utils.formatTimeFromMilliseconds


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaContent(
    media: Media,
    isPlaying: Boolean,
    positionMilliseconds: Long,
    durationMilliseconds: Long,
    artworkBitmap: Bitmap?,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    // Main column that will hold everything.
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // First we want the media art.
        if (artworkBitmap != null) {
            // Image from media metadata.
            Image(
                bitmap = artworkBitmap.asImageBitmap(),
                contentDescription = "Artwork representing the ${media.name} media.",
                modifier = Modifier.size(300.dp).clip(RoundedCornerShape(16.dp))
            )
        } else {
            // Image from default.
            Image(
                painter = painterResource(id = R.drawable.lusonus_final_nobg),
                contentDescription = "Artwork representing the ${media.name} media.",
                modifier = Modifier.size(300.dp).clip(RoundedCornerShape(16.dp))
            )
        }

        // Second we want the title.
        Text(text = media.name)

        // Third we want the media timeline bar.
        // A lot of this stuff could go in MediaScreen but it would be a huge annoyance especially
        // with recomposition so I'm just placing it here.

        // We get the duration.
        // takeIf only takes the value if it's true.
        val duration = (durationMilliseconds.takeIf { it > 0L } ?: 1L)

        // This holds the in between position when you are dragging the slider.
        // Let me break down the big math equation:
        // - The division is to calculate the % completion the slider would be at relative to it's max size
        //   (like obviously a 10 minute song doesn't have a 5x bigger slider than a 2 minute song in actual visual size)
        // - The coerceIn catches the dumb exceptions we have with division, and makes sure the result is between the given values.
        //   Kotlin is amazing for having this.
        var sliderPosition by remember { mutableFloatStateOf((positionMilliseconds.toFloat() / duration.toFloat()).coerceIn(0f, 1f)) }

        // Whether the user is dragging the slider or not.
        var isUserDragging by remember { mutableStateOf(false) }

        // This LaunchedEffect updates the slider position.
        LaunchedEffect(positionMilliseconds, duration) {
            if (!isUserDragging) {
                sliderPosition = (positionMilliseconds.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
            }
        }

        // We set up the slider.
        Slider(
            value = sliderPosition,
            onValueChange = {
                    value ->

                // When the value is changing we update the UI
                sliderPosition = value
                isUserDragging = true
            },
            onValueChangeFinished = {
                // Only when it's finished we update the backend and match the media to the time.

                // We get the target milliseconds by getting the percentage of the actual duration :D.
                val targetMilliseconds = (sliderPosition * duration).toLong().coerceIn(0L, duration)

                // We perform the OnSeekIntent
                onSeekTo(targetMilliseconds)
                isUserDragging = false
            },
            modifier = Modifier.fillMaxWidth()
        )

        // We add a row that contains the time that matches the slider.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatTimeFromMilliseconds((sliderPosition * duration).toLong().coerceIn(0L, duration)))
            Text(text = formatTimeFromMilliseconds(duration))
        }

        // Lastly, we add the controls.
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous button.
            IconButton(onClick = onPrevious) {
                Icon(Icons.Default.FastRewind, contentDescription = "Previous Media.")
            }

            // Play/Pause button.
            if (isPlaying) {
                IconButton(onClick = onPause) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause Media.")
                }
            } else {
                IconButton(onClick = onResume) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play/Resume Media.")
                }
            }

            // Next Button.
            IconButton(onClick = onNext) {
                Icon(Icons.Default.FastForward, contentDescription = "Next Media.")
            }
        }
    }
}
