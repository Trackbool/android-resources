package com.example.musicplayer

import com.example.musicplayer.models.PlayList
import com.example.musicplayer.models.PlayListImpl
import com.example.musicplayer.models.Song
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PlayListImplTest {

    private lateinit var playList: PlayList

    @Before
    fun initialize() {
        playList = PlayListImpl(
            songs = arrayListOf(
                Song(1, title = "Zooming on the sky"),
                Song(2, year = 1994),
                Song(3, title = "We love Jesus Christ", year = 2008),
                Song(4, title = "Yellow Submarine")
            )
        )
    }

    @Test
    fun `Count PlayList length is correct`() {
        Assert.assertEquals(4, playList.getSongsCount())
    }

    @Test
    fun `Add song in the playList`() {
        var newSong = Song(10, title = "Nestor is happy")
        playList.addSong(newSong)
        Assert.assertEquals(newSong, playList.getSong(playList.getSongsCount() - 1))
    }

    @Test
    fun `Add songs list in the playList`() {
        var newSongs = arrayListOf(Song(10), Song(11), Song(12))
        playList.addSongs(newSongs)
        Assert.assertTrue(
            playList.getSongsCount() == 7
                    && playList.getSong(playList.getSongsCount() - 3).id == 10
                    && playList.getSong(playList.getSongsCount() - 2).id == 11
                    && playList.getSong(playList.getSongsCount() - 1).id == 12
        )
    }

    @Test
    fun `Remove song by index`() {
        playList.removeSong(playList.getSongsCount() - 1)
        Assert.assertNotEquals("Yellow Submarine", playList.getSong(playList.getSongsCount() - 1).title)
    }

    @Test
    fun `Remove song by song object`() {
        var song = Song(10, title = "Adri likes sea")
        playList.addSong(song)
        playList.removeSong(song)
        Assert.assertNotEquals("Adri likes sea", playList.getSong(playList.getSongsCount() - 1).title)
    }

    @Test
    fun clearSongs() {
        playList.clearSongs()
        Assert.assertEquals(0, playList.getSongsCount())
    }

    @Test
    fun sortByTitle() {
        playList.sortByTitle()
        Assert.assertTrue(
            "Bad implementation of sort - List is not ordering well by title (ascending)",
            playList.getSong(0).title == "(Unknown)" &&
                    playList.getSong(1).title == "We love Jesus Christ" &&
                    playList.getSong(2).title == "Yellow Submarine" &&
                    playList.getSong(3).title == "Zooming on the sky"
        )
    }

    @Test
    fun `Sort by year ascending`() {
        playList.sortByYear()
        Assert.assertTrue(
            "Bad implementation of sort - List is not ordering well by year (ascending)",
            playList.getSong(0).year == 0
                    && playList.getSong(1).year == 0
                    && playList.getSong(2).year == 1994
                    && playList.getSong(3).year == 2008
        )
    }

    @Test
    fun `Sort by year descending`() {
        playList.sortByYear(false)
        Assert.assertTrue(
            "Bad implementation of sort - List is not ordering well by year (descending)",
            playList.getSong(0).year == 2008
                    && playList.getSong(1).year == 1994
                    && playList.getSong(2).year == 0
                    && playList.getSong(3).year == 0
        )
    }
}