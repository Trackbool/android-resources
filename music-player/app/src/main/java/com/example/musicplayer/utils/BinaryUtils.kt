package com.example.musicplayer.utils

class BinaryUtils {
    companion object {

        fun extractInt(data: ByteArray, pos: Int, endPos: Int): Int {
            val num: String = extractString(data, pos, endPos)
            return num.toInt()
        }

        fun extractString(data: ByteArray, pos: Int, endPos: Int): String {
            val text = StringBuilder()
            for (i in pos until endPos) {
                text.append(data[i].toChar())
            }
            return text.toString().trim()
        }
    }
}