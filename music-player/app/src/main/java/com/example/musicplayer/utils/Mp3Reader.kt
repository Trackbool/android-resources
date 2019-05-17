package com.example.musicplayer.utils

import com.example.musicplayer.models.Mp3Metadata
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Mp3Reader {

    companion object {
        fun loadFile(mp3Stream: InputStream): Mp3Metadata {
            val data = loadDataFromStream(mp3Stream) ?: return Mp3Metadata()
            val header = loadHeader(data)

            return extractData(header)
        }

        private fun loadDataFromStream(mp3Stream: InputStream): ByteArray? {
            var data: ByteArrayOutputStream? = ByteArrayOutputStream()
            val dataBlock = ByteArray(4096)

            try {
                var bytesRead = mp3Stream.read(dataBlock, 0, dataBlock.size)
                while (bytesRead != -1) {
                    data?.write(dataBlock, 0, bytesRead)
                    bytesRead = mp3Stream.read(dataBlock, 0, dataBlock.size)
                }
            } catch (ioe: Throwable) {
                data = null
            } finally {
                mp3Stream.close()
            }
            return data?.toByteArray()
        }

        private fun loadHeader(data: ByteArray): ByteArray {
            val header = ByteArrayOutputStream()
            header.write(data, data.size - 128, 128)
            return header.toByteArray()
        }

        private fun extractData(header: ByteArray): Mp3Metadata {
            val title = BinaryUtils.extractString(header, 3, 33)
            val author = BinaryUtils.extractString(header, 33, 63)
            val album = BinaryUtils.extractString(header, 63, 93)
            val year: Int = try {
                BinaryUtils.extractInt(header, 93, 97)
            } catch (e: NumberFormatException) {
                -1
            }
            return Mp3Metadata(title, author, album, year)
        }
    }
}