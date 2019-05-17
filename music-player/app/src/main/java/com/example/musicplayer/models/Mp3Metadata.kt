package com.example.musicplayer.models

data class Mp3Metadata(
    var title: String = "(Unknown)",
    var author: String = "(Unknown)",
    var album: String = "(Unknown)",
    var year: Int = 0,
    var comment: String = "",
    var genre: Int = -1
)