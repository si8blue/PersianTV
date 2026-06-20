package ir.persiantv.ui.player

import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import ir.persiantv.data.Channel
import ir.persiantv.ui.theme.*

@Composable
fun PlayerScreen(
    channel: Channel?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val exoPlayer = remember {
        val trackSelector = DefaultTrackSelector(context).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()
    }

    LaunchedEffect(channel) {
        channel?.let {
            // Validate channel URL before attempting to play
            if (it.url.isEmpty()) {
                isLoading = false
                hasError = true
                errorMessage = "Invalid URL: Channel URL is empty"
                return@let
            }

            try {
                isLoading = true
                hasError = false
                errorMessage = ""
                val mediaItem = MediaItem.fromUri(it.url)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            } catch (e: Exception) {
                isLoading = false
                hasError = true
                errorMessage = "Failed to load: ${e.message ?: "Unknown error"}"
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        isLoading = false
                        hasError = false
                    }
                    Player.STATE_BUFFERING -> isLoading = true
                    Player.STATE_ENDED -> {
                        isLoading = false
                        hasError = true
                        errorMessage = "Stream ended"
                    }
                    Player.STATE_IDLE -> {}
                }
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                isLoading = false
                hasError = true
                errorMessage = "Playback Error: ${error.message ?: "Unknown error"}"
                error.printStackTrace()
            }
        })
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    BackHandler {
        exoPlayer.stop()
        onBack()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = true
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    controllerShowTimeoutMs = 3000
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = PersianBlue,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Loading...",
                        style = BodyLarge.copy(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (hasError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Playback Error",
                        style = TitleLarge.copy(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = errorMessage,
                        style = BodyMedium.copy(color = Color.White.copy(alpha = 0.9f)),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Press back to return",
                        style = BodySmall.copy(color = Color.White.copy(alpha = 0.7f)),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        channel?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = it.title,
                        style = TitleMedium.copy(color = Color.White),
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
