package com.example.lusonus.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

// LAND OF CONSTANTS
// EVERYTHING WE NEED TO TRACK

// Action constants (more state than info).
const val ACTION_PLAY_URI = "com.example.lusonus.action.PLAY_URI"
const val ACTION_PAUSE = "com.example.lusonus.action.PAUSE"
const val ACTION_RESUME = "com.example.lusonus.action.RESUME"
const val ACTION_SEEK_TO = "com.example.lusonus.action.SEEK_TO"
const val ACTION_NEXT = "com.example.lusonus.action.NEXT"
const val ACTION_PREV = "com.example.lusonus.action.PREV"
const val ACTION_STOP = "com.example.lusonus.action.STOP"
const val ACTION_PLAYBACK_STATE = "com.example.lusonus.action.PLAYBACK_STATE"

// Extra constants (more info than state).
const val EXTRA_URI = "media_uri"
const val EXTRA_SEEK_POSITION = "com.example.lusonus.extra.SEEK_POSITION"
const val EXTRA_IS_PLAYING = "com.example.lusonus.extra.IS_PLAYING"
const val EXTRA_POSITION = "com.example.lusonus.extra.POSITION"
const val EXTRA_DURATION = "com.example.lusonus.extra.DURATION"
const val EXTRA_MEDIA_NAME = "com.example.lusonus.extra.MEDIA_NAME"
const val EXTRA_ARTWORK_BYTES = "com.example.lusonus.extra.ARTWORK_BYTES"

class PlayerService : MediaSessionService() {
    // This is easily the most important thing in our entire app.
    // It's what lets us play media (videos and music).
    // I'm using lateinit since we should only make it when we need it (good for performance).
    private lateinit var player: ExoPlayer

    // This is the second most important thing in our app, since it lets
    // us to essentially allow the playback to happen anywhere.
    private lateinit var session: MediaSession

    private lateinit var progressUpdate: Job

    // This is what gets called when we are creating the player service.
    @OptIn(UnstableApi::class)
    override fun onCreate() {
        // We need to make sure to call the MediaSessionService since this is just a variant
        // that allows us to have an ExoPlayer.
        super.onCreate()
        createNotificationChannel()

        // Sends updates to update the UI (needed this for time/sliders)
        progressUpdate =
            CoroutineScope(Dispatchers.Main).launch {
                while (isActive) {
                    // Sends broadcast update.
                    sendPlaybackStateBroadcast()

                    // Updates every second.
                    delay(1000)
                }
            }

        // This actually sets up the ExoPlayer, check out this link:
        // https://developer.android.com/media/media3/exoplayer/hello-world
        player =
            ExoPlayer.Builder(this).build().apply {
                // Everything in here are special actions that can happen that I needed to change.

                addListener(
                    object : Player.Listener {
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            sendPlaybackStateBroadcast()

                            // We want to place the notification if it's not there.
                            if (isPlaying) ensureForegroundNotification()
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            sendPlaybackStateBroadcast()
                        }

                        override fun onPositionDiscontinuity(reason: Int) {
                            sendPlaybackStateBroadcast()
                        }
                    },
                )
            }

        // This sets up the session with the exoplayer, so we can do stuff anywhere.
        // This also does so so so much behind the scenes. Media3 automatically handles
        // and manages notifications for us and stuff.
        session = MediaSession.Builder(this, player).build()
    }

    // This is an inherited method I had to implement. It's basically just a getter.
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = session

    // We need to override this so we set the media intent properly.
    // There's a lot of stuff going on but from what I understood not doing an
    // override here causes a lot of edge cases bugs since media could just not be
    // properly prepared, etc. It was also recommended to override the notifications
    // but since that was never a focus of our application I opted to not do so.
    // Added this suppress since we REALLY don't want to call the parent version.
    @SuppressLint("MissingSuperCall")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        intent?.let { handleIntent(it) }

        // Hover over and read what this does, it's really important.
        return START_STICKY
    }

    // Now that we have multiple intents possible, I created this function to manage them
    // all with a switch case statement.
    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            // When the action is "we want to play an uri".
            ACTION_PLAY_URI -> {
                // Long story short there is information we aren't getting when we import the media
                // so this gets all the stuff we are missing.
                val uriString = intent.getStringExtra(EXTRA_URI) ?: return

                // If the currently playing media is the same as the one about to play just don't
                // play it.
                val current = player.currentMediaItem
                if (current?.localConfiguration?.uri.toString() == uriString) {
                    if (!player.isPlaying) player.play()
                    return
                }

                // From the string we get the MediaItem (not ours this is a Media3 thing).
                val item = MediaItem.fromUri(uriString)

                // We also set the MediaItem to the player and then prepare the media before playing,
                // yes it's goofy I know.
                player.setMediaItem(item)
                player.prepare()
                player.play()

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }

            // When the action is "we want to pause the media".
            ACTION_PAUSE -> {
                player.pause()

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }

            // When the action is "we want to resume the media".
            ACTION_RESUME -> {
                player.play()

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }

            // When the action is "we want to drag the timeline".
            ACTION_SEEK_TO -> {
                // We get the position from the intent.
                val position = intent.getLongExtra(EXTRA_SEEK_POSITION, -1L)

                // If the position is somewhere valid.
                if (position >= 0L) {
                    // We seek to it in the player.
                    player.seekTo(position)
                }

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }

            // When the action is "we want to play the next media".
            ACTION_NEXT -> {
                // TODO: this is prepared for queue integration LATER
                // This checks to see if there is something loaded before playing the next one.
                if (player.hasNextMediaItem()) {
                    player.seekToNext()
                } else {
                    // If there's nothing in the queue we skip to the end of the current media.
                    val duration = player.duration
                    if (duration > 0) {
                        // End the duration right before the end so it ends normally.
                        player.seekTo(duration - 50)
                    }
                }

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }

            // When the action is "we want to play the previous media".
            ACTION_PREV -> {
                // TODO: this is prepared for queue integration LATER
                // This checks to see if there is something loaded before playing the previous one.
                if (player.hasPreviousMediaItem()) {
                    player.seekToPrevious()
                } else {
                    player.seekTo(0) // Will seek to init uri if there's no previous.
                }
            }

            // When the action is "stop the application".
            ACTION_STOP -> {
                // We stop the player
                player.stop()

                // We remove it from the foreground since the player is stopped.
                // Not to be confused with paused.
                stopForeground(STOP_FOREGROUND_REMOVE)

                // We stop the service.
                stopSelf()

                // We send out that it has been updated!
                sendPlaybackStateBroadcast()
            }
        }
    }

    // This sends back all the information we can possibly need OUTSIDE of here.
    private fun sendPlaybackStateBroadcast() {
        try {
            // We set up an intent with all the extras properly mapped to information.
            val intent =
                Intent(ACTION_PLAYBACK_STATE).apply {
                    // We map the isPlaying.
                    putExtra(EXTRA_IS_PLAYING, player.isPlaying)

                    // We map the position we are at in the media, it's relative to the duration.
                    putExtra(EXTRA_POSITION, player.currentPosition)

                    // We map the duration.
                    // We check to see if the duration is valid and if it's not we send back 0L
                    // (as in 0 long). We are using longs since it's milliseconds.
                    putExtra(EXTRA_DURATION, if (player.duration > 0) player.duration else 0L)
                }

            // Adds the currently playing media's name.
            val currentItem = player.currentMediaItem

            // If the item exists (there could be nothing playing)
            // we go into it and get the meta data from the uri to actually send back the name.
            currentItem?.localConfiguration?.uri?.let { uri ->

                // Adds an extra to the intent to send back.
                intent.putExtra(EXTRA_MEDIA_NAME, uri.toString())
            }

            // Haha now we have artwork, basically the best way I found to do this is to
            // just extra embedded bytes (if there are any) from the metadata.
            val artworkBytes = extractEmbeddedArtworkBytes()

            // If the artwork is not null, meaning it found something, we add it to the intent.
            if (artworkBytes != null) {
                intent.putExtra(EXTRA_ARTWORK_BYTES, artworkBytes)
            }

            // We wrap up the intent with a bow and tie
            intent.setPackage(packageName)

            // and we send it off!
            sendBroadcast(intent)
        } catch (e: Exception) // If somewhere in all of that there's a problem...
        {
            // I need to see the message lmao
            e.printStackTrace()
        }
    }

    // This is how we get the artwork (in bytes) from the meta data.
    private fun extractEmbeddedArtworkBytes(): ByteArray? {
        // We use another try catch statement... I think this is the first time I was like
        // I NEED A TRY CATCH LMAO! You try debugging bytes...
        try {
            // If there's no current media we can't even do anything.
            val current = player.currentMediaItem ?: return null

            // If there's no uri we can't even extract the art.
            val uri = current.localConfiguration?.uri ?: return null

            // A big thank you to android studio.
            val mmr = MediaMetadataRetriever()

            // We set the drill to this context and give it the uri.
            mmr.setDataSource(this, uri)

            // We have the art!
            val art = mmr.embeddedPicture

            // Releases it from memory.
            mmr.release()

            // If the art is not evil and bad we give it to the gods.
            if (art != null) return art
            // If it was bad, then our fantastic UI will have a default :D
        } catch (e: Exception) {
            // As expected when something breaks I want to see what.
            e.printStackTrace()
        }

        // If it was bad, then our fantastic UI will have a default :D.
        return null
    }

    // This is a minimal notification so the phone treats our service as the foreground when
    // there's something playing.
    private fun ensureForegroundNotification() {
        // This makes a notification manager. It's a weird thing from java that we need to use
        // in kotlin to make it work.
        val notificationManager = getSystemService(NotificationManager::class.java)

        // This is what build the notification, it's where we info dump.
        val builder =
            NotificationCompat
                .Builder(
                    this,
                    "media_channel",
                ).setContentTitle("Lusonus")
                .setContentText("Playing")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setPriority(
                    NotificationCompat.PRIORITY_LOW,
                ).setCategory(NotificationCompat.CATEGORY_TRANSPORT)

        // This is what starts it in the foreground.
        startForeground(1, builder.build())
    }

    // Since we are Android version 13 + we need to do some annoying notification channel
    // permissions at runtime.
    private fun createNotificationChannel() {
        // The options were to OPT Build.VERSION_CODES.O or to validate the version to be
        // greater than that, I did the latter.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creates a channel to allow notifications.
            val channel =
                NotificationChannel(
                    "media_channel",
                    "Media Playback",
                    NotificationManager.IMPORTANCE_LOW,
                ).apply {
                    description = "Media playback controls."
                }

            // This opens the service with the phone to allow notifications.
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    // This is really important since we don't want to have memory leaks in this app especially.
    override fun onDestroy() {
        // Let go of the session.
        session.release()

        // Let go of the player.
        player.release()

        // Destroy everything.
        super.onDestroy()
    }

    // Binds the intent... basically links intent with service.
    override fun onBind(intent: Intent?): IBinder {
        super.onBind(intent)
        // We need this so MediaSessionService uses the session for binding.
        return PlayerBinder()
    }

    // In PlayerService
    inner class PlayerBinder : Binder() {
        fun getPlayer(): ExoPlayer = player
    }
}
