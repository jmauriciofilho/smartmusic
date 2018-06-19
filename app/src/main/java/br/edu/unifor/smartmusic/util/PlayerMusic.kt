package br.edu.unifor.smartmusic.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import br.edu.unifor.smartmusic.entity.Music

class PlayerMusic(val musics: List<Music>) {

    private var mediaPlayer = MediaPlayer()

    fun play(id: Int){

        for (music in musics){
            if (music._id == id){
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer.setDataSource(music.uri)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }

    }

    fun pause(id: Int){
        for (music in musics){
            if (music._id == id){
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer.setDataSource(music.uri)
                mediaPlayer.prepare()
                mediaPlayer.pause()
            }
        }
    }

    fun avancar(){

    }

    fun voltar(){

    }
}
