package br.edu.unifor.smartmusic.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import br.edu.unifor.smartmusic.activity.MainActivity
import br.edu.unifor.smartmusic.entity.Music

class MediaPlayerService: Service(), MediaPlayer.OnPreparedListener {

    private val ACTION_PLAY = "br.edu.unifor.smartmusic.PLAY"
    var musics: List<Music>? = null
    var position: Int = 0

    companion object {
        public var mMediaPlayer: MediaPlayer? = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("teste", "aquiiiiiiiiiiiiiii")
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        musics = MainActivity.getMp3Songs(this)
        var stringPath = android.net.Uri.parse(musics?.get(intent.getIntExtra("position", 0))!!.uri).getPath()
        Log.d("teste", "testesssssssss")
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setDataSource(stringPath)
        mMediaPlayer?.setOnPreparedListener(this)
        mMediaPlayer?.prepareAsync()


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        if (mMediaPlayer?.isPlaying!!) {
            mMediaPlayer?.stop()
        }
        mMediaPlayer?.release()
    }

    override fun onPrepared(mp: MediaPlayer) {
        if (!mp.isPlaying) {
            mp.start()
        }else{
            mp.pause()
        }
    }
}