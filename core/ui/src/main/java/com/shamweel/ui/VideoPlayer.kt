package com.shamweel.ui

import android.net.Uri
import android.util.Xml
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.shamweel.gallery.core.ui.R
import org.xmlpull.v1.XmlPullParser

@Composable
@OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(
    modifier: Modifier = Modifier,
    uri: Uri?,
    isAdjustSelfAspectRatio: Boolean = false,
    isAutoPlay: Boolean = false,
    isScaleCrop: Boolean = false,
    isUseController: Boolean = false,
    isMute: Boolean = true
) {

    if (uri == null) return
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val exoPlayer = remember {
        val loadControl = DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(false)
            .setBufferDurationsMs(1000, 1000, 1000, 1000)
            .build()


        ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .build().apply {
                if (isMute) {
                    volume = 0f
                }
                playWhenReady = isAutoPlay
                videoScalingMode =
                    if (isScaleCrop) C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING else C.VIDEO_SCALING_MODE_DEFAULT
                repeatMode = Player.REPEAT_MODE_ONE
            }
    }


    val initExoPlayer = {
        exoPlayer.apply {
            val defaultDataSourceFactory = DefaultDataSource.Factory(context)
            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                context,
                defaultDataSourceFactory
            )
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(uri)
                )
            setMediaSource(mediaSource)

            prepare()
            if (isAutoPlay) {
                play()
            }
        }
    }

    LaunchedEffect(Unit) {
        initExoPlayer()
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier) {
        AndroidView(
            modifier = Modifier
                .then(
                    if (isAdjustSelfAspectRatio)
                        Modifier.wrapContentSize() else Modifier.matchParentSize()
                ),
            factory = { _ ->
                val parser = context.resources.getXml(R.xml.attrs)
                var type = 0
                while (type != XmlPullParser.END_DOCUMENT && type != XmlPullParser.START_TAG) {
                    type = parser.next()
                }
                val attrs = Xml.asAttributeSet(parser)
                PlayerView(context, attrs).apply {
                    player = exoPlayer
                    useController = isUseController
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    clipToOutline = true
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        if (it.player?.isPlaying == false && isAutoPlay) {
                            it.player?.play()
                        }
                    }

                    else -> Unit
                }
            },
        )

    }
}