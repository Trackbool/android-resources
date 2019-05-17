package com.example.musicplayer.models

interface PlayList {
    fun getSong(index: Int) : Song
    fun addSong(song: Song)
    fun removeSong(song: Song)
    fun removeSong(index: Int)
    fun clearSongs()
    fun addSongs(songs: List<Song>)
    fun sortByTitle()
    fun sortByYear(ascending: Boolean = true)
    fun getSongsCount() : Int
}