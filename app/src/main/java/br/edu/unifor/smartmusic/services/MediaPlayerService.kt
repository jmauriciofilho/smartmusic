package br.edu.unifor.smartmusic.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import br.edu.unifor.smartmusic.entity.Music

class MediaPlayerService: Service(), MediaPlayer.OnPreparedListener {

    private val ACTION_PLAY = "br.edu.unifor.smartmusic.PLAY"
    private lateinit var mMediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        Log.d("teste", intent.action)

        if (intent.action == (ACTION_PLAY)) {
            Log.d("teste", "testesssssssss")
            mMediaPlayer = MediaPlayer()
            mMediaPlayer.setOnPreparedListener(this)
            mMediaPlayer.prepareAsync()
        }

    }

    override fun onDestroy() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
        }
        mMediaPlayer.release()
    }

    override fun onPrepared(mp: MediaPlayer) {
        if (!mp.isPlaying) {
            mp.start()
        }else{
            mp.pause()
        }
    }
}