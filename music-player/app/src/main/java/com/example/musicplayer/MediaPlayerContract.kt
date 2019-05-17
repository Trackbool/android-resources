package com.example.musicplayer

interface MediaPlayerContract {
    interface View{
        fun showCurrentSongTitle(name: String)
        fun showPaused()
        fun showPlaying()
    }
    interface Presenter{
        fun onPlayButtonPressed()
        fun onPauseButtonPressed()
        fun onPreviousButtonPressed()
        fun onNextButtonPressed()
    }
}