package br.edu.unifor.smartmusic.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import br.edu.unifor.smartmusic.entity.Music

class PlayerMusic(val context: Context) {

    private val mediaPlayer = MediaPlayer()

    fun play(music: Music){
        val uri = music.uri
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun pause(music: Music){
        val uri = music.uri
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepare()
        mediaPlayer.pause()
    }

    fun avancar(){

    }

    fun voltar(){

    }
}