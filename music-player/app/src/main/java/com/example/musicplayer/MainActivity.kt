package com.example.musicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.musicplayer.models.AudioManagerImpl
import com.example.musicplayer.models.PlayListImpl
import com.example.musicplayer.models.Song
import com.example.musicplayer.utils.Mp3Reader

class MainActivity : AppCompatActivity(), MediaPlayerContract.View {
    private var mSongTitle: TextView? = null
    private var mPlay: Button? = null
    private var mPause: Button? = null
    private var mPrevious: Button? = null
    private var mNext: Button? = null
    private var presenter: MediaPlayerContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initViews()
        initListeners()
    }

    private fun init() {
        presenter = PresenterImpl(
            this,
            AudioManagerImpl(
                applicationContext, PlayListImpl(
                    arrayListOf(
                        mp3ToSong(R.raw.mpthreetest)
                    )
                )
            )
        )
    }

    private fun mp3ToSong(songId: Int): Song {
        val song = Song(songId)
        val mp3Data = Mp3Reader.loadFile(resources.openRawResource(songId))
        song.title = mp3Data.title
        song.author = mp3Data.author
        song.album = mp3Data.album
        song.year = mp3Data.year
        return song
    }

    private fun initViews() {
        mSongTitle = findViewById(R.id.textViewSongTitle)
        mPlay = findViewById(R.id.buttonPlay)
        mPause = findViewById(R.id.buttonPause)
        mPrevious = findViewById(R.id.buttonPrevious)
        mNext = findViewById(R.id.buttonNext)
    }

    private fun initListeners() {
        mPlay?.setOnClickListener { presenter?.onPlayButtonPressed() }
        mPause?.setOnClickListener { presenter?.onPauseButtonPressed() }
        mPrevious?.setOnClickListener { presenter?.onPreviousButtonPressed() }
        mNext?.setOnClickListener { presenter?.onNextButtonPressed() }
    }

    override fun showCurrentSongTitle(name: String) {
        mSongTitle?.text = name
    }

    override fun showPaused() {
        Log.d("STATUS_MEDIAPLAYER", "PAUSED")
    }

    override fun showPlaying() {
        Log.d("STATUS_MEDIAPLAYER", "PLAYING")
    }
}
