package com.example.lusonus.ui.screens.MediaScreen

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import com.example.lusonus.services.ACTION_NEXT
import com.example.lusonus.services.ACTION_PAUSE
import com.example.lusonus.services.ACTION_PLAYBACK_STATE
import com.example.lusonus.services.ACTION_PLAY_URI
import com.example.lusonus.services.ACTION_PREV
import com.example.lusonus.services.ACTION_RESUME
import com.example.lusonus.services.ACTION_SEEK_TO
import com.example.lusonus.services.EXTRA_ARTWORK_BYTES
import com.example.lusonus.services.EXTRA_DURATION
import com.example.lusonus.services.EXTRA_IS_PLAYING
import com.example.lusonus.services.EXTRA_MEDIA_NAME
import com.example.lusonus.services.EXTRA_POSITION
import com.example.lusonus.services.EXTRA_SEEK_POSITION
import com.example.lusonus.services.EXTRA_URI
import com.example.lusonus.services.PlayerService
import com.example.lusonus.ui.composables.Layout.MainLayout

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaScreen(mediaName: String) {
    // Gets the media view model, calls the media factory so we can pass the media name to the
    // view model to be able to get the specific media.
    val viewModel: MediaViewModel = viewModel(factory = MediaViewModelFactory(mediaName))

    // Gets the local context.
    val context = LocalContext.current

    // This registers the BroadcastReceiver so we are able to get playback updates from the service.
    // The service is PlayerService.
    DisposableEffect(key1 = viewModel) {
        // We add an intent filter for the playback state.
        val filter = IntentFilter(ACTION_PLAYBACK_STATE)

        val receiver =
            object : BroadcastReceiver() {
                override fun onReceive(
                    context: Context?,
                    intent: Intent?,
                ) {
                    // Quits if the intent is invalid.
                    if (intent == null) return

                    // Gets info from the intents.
                    val isPlaying = intent.getBooleanExtra(EXTRA_IS_PLAYING, false)
                    val position = intent.getLongExtra(EXTRA_POSITION, 0L)
                    val duration = intent.getLongExtra(EXTRA_DURATION, 0L)
                    val art = intent.getByteArrayExtra(EXTRA_ARTWORK_BYTES)
                    val name = intent.getStringExtra(EXTRA_MEDIA_NAME)

                    viewModel.updatePlaybackState(isPlaying, position, duration, art)

                    if(name != null)
                        viewModel.updateCurrentMedia(name.toUri(), context!!)
                }
            }

        // Adds the receiver and the filter to the context.
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )

        // We want to get rid of the register from the context when it's done so it doesn't get
        // massive and starts to slow down the phone.
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    val player = remember { mutableStateOf<ExoPlayer?>(null) }
    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val binder = binder as PlayerService.PlayerBinder
                player.value = binder.getPlayer()
            }
            override fun onServiceDisconnected(name: ComponentName?) { player.value = null }
        }
    }

    LaunchedEffect(Unit) {
        val intent = Intent(context, PlayerService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    // Starts the Media when the page opens.
    LaunchedEffect(viewModel.media) {
        // The PlayerService::class.java is the companion object for PlayerService.

        // Sets up the proper intent.
        val intent =
            Intent(context, PlayerService::class.java).apply {
                action = if (viewModel.isPlaying || !viewModel.hasStarted) ACTION_PLAY_URI else ACTION_PAUSE
                putExtra(EXTRA_URI, viewModel.media?.uri.toString())
            }

        viewModel.toggleStartedPlaying()

        // Start the foreground service with this intent.
        context.startForegroundService(intent)
    }

    val cleanName = remember(mediaName) { mediaName.substringBeforeLast(".") }

    MainLayout(
        content = {
            MediaContent(
                media = viewModel.media!!,
                isPlaying = viewModel.isPlaying,
                positionMilliseconds = viewModel.positionMilliseconds,
                durationMilliseconds = viewModel.durationMilliseconds,
                artworkBitmap = viewModel.artworkBitmap,
                player = player.value,
                onPause = {
                    // Sets up the proper intent.
                    val intent =
                        Intent(context, PlayerService::class.java).apply {
                            action = ACTION_PAUSE
                        }

                    // Start the foreground service with this intent.
                    context.startForegroundService(intent)
                },
                onResume = {
                    // Sets up the proper intent.
                    val intent =
                        Intent(context, PlayerService::class.java).apply {
                            action = ACTION_RESUME
                        }

                    // Start the foreground service with this intent.
                    context.startForegroundService(intent)
                },
                onSeekTo = { positionMilliseconds ->

                    // Sets up the proper intent.
                    val intent =
                        Intent(context, PlayerService::class.java).apply {
                            action = ACTION_SEEK_TO
                            putExtra(EXTRA_SEEK_POSITION, positionMilliseconds)
                        }

                    // Start the foreground service with this intent.
                    context.startForegroundService(intent)
                },
                onNext = {
                    // Sets up the proper intent.
                    val intent =
                        Intent(context, PlayerService::class.java).apply {
                            action = ACTION_NEXT
                        }

                    // Start the foreground service with this intent.
                    context.startForegroundService(intent)
                },
                onPrevious = {
                    // Sets up the proper intent.
                    val intent =
                        Intent(context, PlayerService::class.java).apply {
                            action = ACTION_PREV
                        }

                    // Start the foreground service with this intent.
                    context.startForegroundService(intent)
                },
            )
        },
        screenTitle = cleanName,
        bottomBar = { },
    )
}
