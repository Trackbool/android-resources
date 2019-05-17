package com.example.musicplayer

import com.example.musicplayer.models.AudioManager

class PresenterImpl(
    var view: MediaPlayerContract.View,
    var audioManager: AudioManager
) : MediaPlayerContract.Presenter {
    private var paused: Boolean = true

    override fun onPlayButtonPressed() {
        if (paused) {
            audioManager.play()
            view.showCurrentSongTitle(audioManager.getCurrentSong().title)
            view.showPlaying()
        }
        paused = false
    }

    override fun onPauseButtonPressed() {
        if (!paused) {
            audioManager.pause()
            view.showPaused()
        }
        paused = true
    }

    override fun onPreviousButtonPressed() {
        audioManager.loadPreviousSong()
        view.showCurrentSongTitle(audioManager.getCurrentSong().title)
    }

    override fun onNextButtonPressed() {
        audioManager.loadNextSong()
        view.showCurrentSongTitle(audioManager.getCurrentSong().title)
    }
}