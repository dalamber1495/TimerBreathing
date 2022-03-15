package com.androiddev.timerbreathing.exoplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.androiddev.timerbreathing.utils.Constants.MEDIA_ROOT_ID
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MusicService : MediaBrowserServiceCompat() {
    private var mediaSession: MediaSessionCompat? = null
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private var exoPlayer: SimpleExoPlayer? = null
    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
            uri?.let {
                val mediaSource = extractorFactory.createMediaSource(uri)
                play(mediaSource)
            }
        }

        override fun onStop() {
            super.onStop()
            stop()
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializePlayer()
        initializeExtractor()
        attrs = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        mediaSession = MediaSessionCompat(baseContext, "tag for debugging").apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())

            setCallback(mediaSessionCallback)

            setSessionToken(sessionToken)
            isActive = true
        }
    }

    private var attrs: AudioAttributes? = null

    private fun play(mediaSource: MediaSource) {
        if (exoPlayer == null) initializePlayer()
        exoPlayer?.apply {
            attrs?.let {
                attrs = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .build()
            }
            setAudioAttributes(attrs, true)
            prepare(mediaSource)
            exoPlayer?.playWhenReady = true
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
            mediaSession?.isActive = true
        }
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            this, DefaultRenderersFactory(baseContext), DefaultTrackSelector(),
            DefaultLoadControl()
        )
    }


    private fun stop() {
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
        exoPlayer = null
        updatePlaybackState(PlaybackStateCompat.STATE_NONE)
        mediaSession?.isActive = false
        mediaSession?.release()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    private fun updatePlaybackState(state: Int) {
        mediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                state, 0L, 1.0f
            ).build()
        )
    }

    private lateinit var extractorFactory: ExtractorMediaSource.Factory

    private fun initializeExtractor() {
        val userAgent = Util.getUserAgent(baseContext, "Application Name")
        extractorFactory = ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        return
    }
}
