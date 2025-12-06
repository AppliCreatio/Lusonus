package com.example.lusonus.ui.screens.MediaScreen

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.lusonus.R
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.ui.composables.MediaComposables.VideoPlayer
import com.example.lusonus.ui.utils.formatTimeFromMilliseconds
import com.example.lusonus.ui.utils.getFileName
import com.example.lusonus.ui.utils.isVideo

@Composable
fun MediaContent(
    media: Media,
    isPlaying: Boolean,
    positionMilliseconds: Long,
    durationMilliseconds: Long,
    artworkBitmap: Bitmap?,
    player: ExoPlayer?,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Main column that will hold everything.
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        val cleanName = remember(media.name) { media.name.substringBeforeLast(".") }

        // First we want the media art.
        val painter =
            artworkBitmap?.asImageBitmap()?.let { BitmapPainter(it) }
                ?: painterResource(id = R.drawable.lusonus_placeholder)

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
        var sliderPosition by remember {
            mutableFloatStateOf(
                (positionMilliseconds.toFloat() / duration.toFloat()).coerceIn(
                    0f,
                    1f,
                ),
            )
        }

        // Whether the user is dragging the slider or not.
        var isUserDragging by remember { mutableStateOf(false) }

        // This LaunchedEffect updates the slider position.
        LaunchedEffect(positionMilliseconds, duration) {
            if (!isUserDragging) {
                sliderPosition =
                    (positionMilliseconds.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
            }
        }

        val isVideo = media.uri.isVideo(context) || media.name.endsWith(".mp4", ignoreCase = true)

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isVideo) {
                    player?.let { VideoPlayer(it, modifier = Modifier.weight(1f)) }
                } else {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(cleanName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(formatTimeFromMilliseconds((sliderPosition * duration).toLong()))
                        Text(formatTimeFromMilliseconds(duration))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onPrevious) { Icon(Icons.Default.FastRewind, null) }
                        IconButton(onClick = if (isPlaying) onPause else onResume) {
                            Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                        }
                        IconButton(onClick = onNext) { Icon(Icons.Default.FastForward, null) }
                    }
                }
            }
        } else {
            if (isVideo) {
                if (player != null)
                    VideoPlayer(player)
            } else {
                Image(
                    painter = painter,
                    contentDescription = "Artwork representing the ${media.name} media.",
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp)),
                )
            }

            // Second we want the title.
            Text(text = cleanName, maxLines = 1, overflow = TextOverflow.Ellipsis)

            // We set up the slider.
            Slider(
                value = sliderPosition,
                onValueChange = { value ->

                    // When the value is changing we update the UI
                    sliderPosition = value
                    isUserDragging = true
                },
                onValueChangeFinished = {
                    // Only when it's finished we update the backend and match the media to the time.

                    // We get the target milliseconds by getting the percentage of the actual duration :D.
                    val targetMilliseconds =
                        (sliderPosition * duration).toLong().coerceIn(0L, duration)

                    // We perform the OnSeekIntent
                    onSeekTo(targetMilliseconds)
                    isUserDragging = false
                },
                modifier = Modifier.fillMaxWidth(),
            )

            // We add a row that contains the time that matches the slider.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text =
                        formatTimeFromMilliseconds(
                            (sliderPosition * duration).toLong().coerceIn(0L, duration),
                        ),
                )
                Text(text = formatTimeFromMilliseconds(duration))
            }

            // Lastly, we add the controls.
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
}
