package com.example.musicplayer.models

class PlayListImpl(var songs: ArrayList<Song> = ArrayList()) : PlayList {
    override fun getSongsCount(): Int {
        return songs.size
    }

    override fun getSong(index: Int): Song {
        return songs[index]
    }

    override fun addSong(song: Song) {
        songs.add(song)
    }

    override fun removeSong(song: Song) {
        songs.remove(song)
    }

    override fun removeSong(index: Int) {
        songs.removeAt(index)
    }

    override fun clearSongs() {
        songs.clear()
    }

    override fun addSongs(songs: List<Song>) {
        this.songs.addAll(songs)
    }

    override fun sortByTitle() {
        songs.sortBy { s -> s.title }
    }

    override fun sortByYear(ascending: Boolean) {
        if (ascending) {
            songs.sortBy { s -> s.year }
        } else {
            songs.sortByDescending { s -> s.year }
        }
    }
}