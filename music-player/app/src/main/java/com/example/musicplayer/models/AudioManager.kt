package com.example.musicplayer.models

interface AudioManager {
    var playList : PlayList

    fun play()
    fun stop()
    fun pause()
    fun restart()
    fun setOnSongFinish(codeToExecute: Runnable)
    fun getCurrentSong(): Song
    fun loadNextSong(play: Boolean = true)
    fun loadPreviousSong(play: Boolean = true)
}