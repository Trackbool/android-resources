package com.example.musicplayer.models

import android.content.Context
import android.media.MediaPlayer

class AudioManagerImpl(private var context: Context, override var playList: PlayList) : AudioManager {
    private var currentSongIndex: Int = 0
    private var mediaPlayer: MediaPlayer? = null

    init {
        mediaPlayer = loadSong(playList.getSong(currentSongIndex).id)
    }

    override fun getCurrentSong(): Song = playList.getSong(currentSongIndex)

    override fun play() {
        mediaPlayer?.start()
    }

    private fun loadSong(songId: Int): MediaPlayer? {
        var player: MediaPlayer? = null
        if (playList.getSongsCount() > currentSongIndex) {
            player = MediaPlayer.create(context, songId)
        }
        return player
    }

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun restart() {
        mediaPlayer?.reset()
    }

    override fun setOnSongFinish(codeToExecute: Runnable) {
        mediaPlayer?.setOnCompletionListener {
            codeToExecute.run()
        }
    }

    override fun loadNextSong(play: Boolean) {
        stop()
        next()
        if (play)
            play()
    }

    override fun loadPreviousSong(play: Boolean) {
        stop()
        previous()
        if (play)
            play()
    }

    private fun next() {
        currentSongIndex = when {
            (playList.getSongsCount() - 1) > currentSongIndex -> ++currentSongIndex
            else -> 0
        }
        mediaPlayer = loadSong(playList.getSong(currentSongIndex).id)
    }

    private fun previous() {
        currentSongIndex = when {
            (currentSongIndex) > 0 -> --currentSongIndex
            else -> playList.getSongsCount() - 1
        }
        mediaPlayer = loadSong(playList.getSong(currentSongIndex).id)
    }
}