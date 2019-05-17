package com.example.musicplayer.models

data class Song(
    var id: Int,
    var title: String = "(Unknown)",
    var author: String = "(Unknown)",
    var album: String = "(Unknown)",
    var year: Int = 0
) {
    fun getYear(): String {
        return if (year > 0) year.toString()
        else "(Unknown)"
    }
}