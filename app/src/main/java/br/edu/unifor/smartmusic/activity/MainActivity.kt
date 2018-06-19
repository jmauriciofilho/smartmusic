package br.edu.unifor.smartmusic.activity

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.edu.unifor.smartmusic.R
import br.edu.unifor.smartmusic.entity.Music
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.util.Log
import br.edu.unifor.smartmusic.adapter.MusicAdapter
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.view.View
import br.edu.unifor.smartmusic.services.MediaPlayerService


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mMusics: RecyclerView

    private lateinit var mRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewLayoutManager: LinearLayoutManager

    private lateinit var musics: MutableList<Music>

    private lateinit var mPlayBtn: FloatingActionButton
    private lateinit var mPauseBtn: FloatingActionButton
    private lateinit var mStopBtn: FloatingActionButton
    private lateinit var mAvancarBtn: FloatingActionButton
    private lateinit var mVoltarBtn: FloatingActionButton

    private lateinit var mpService: Intent

    private var isPause = false
    var position: Int = 0

    companion object {
        fun getMp3Songs(ctx: Context): List<Music> {
            val list =  ArrayList<Music>()
            val allSongsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
            val cursor = ctx.contentResolver.query(allSongsUri, null, null, null, selection)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        val music = Music(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                                cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
                        list.add(music)
                        //                    album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        //                    int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        //                    int artist_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }

            return list
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions(this)

        mPlayBtn = findViewById(R.id.play_btn)
        mPauseBtn = findViewById(R.id.pause_btn)
        mStopBtn = findViewById(R.id.stop_btn)
        mAvancarBtn = findViewById(R.id.avancar_btn)
        mVoltarBtn = findViewById(R.id.voltar_btn)

        musics = getMp3Songs(this).toMutableList()

        mRecyclerViewAdapter = MusicAdapter(this, musics, MainActivity@this)
        mRecyclerViewLayoutManager = LinearLayoutManager(this)

        mMusics = findViewById<RecyclerView>(R.id.recycler_view_musics).apply {
            setHasFixedSize(false)
            layoutManager = mRecyclerViewLayoutManager
            adapter = mRecyclerViewAdapter
        }

        mPlayBtn.setOnClickListener(this)
        mPauseBtn.setOnClickListener(this)
        mStopBtn.setOnClickListener(this)
        mAvancarBtn.setOnClickListener(this)
        mVoltarBtn.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.play_btn -> {
                if (!MediaPlayerService.mMediaPlayer?.isPlaying!!) {
                    if (isPause){
                        MediaPlayerService.mMediaPlayer?.start()
                    }else{
                        mpService = Intent(this, MediaPlayerService::class.java)
                        startService(mpService)
                    }
                }
            }
            R.id.pause_btn -> {
                if (MediaPlayerService.mMediaPlayer?.isPlaying!!) {
                    isPause = true
                    MediaPlayerService.mMediaPlayer?.pause()
                }
            }
            R.id.stop_btn -> {
                if (MediaPlayerService.mMediaPlayer?.isPlaying!!) {
                    mpService = Intent(this, MediaPlayerService::class.java)
                    stopService(mpService)
                }
            }
            R.id.avancar_btn -> {
                if (position < musics.size - 1){
                    stopService(mpService)
                    position += 1
                    mpService = Intent(this, MediaPlayerService::class.java)
                    mpService.putExtra("position", position)
                    startService(mpService)

                }
            }
            R.id.voltar_btn -> {
                if (position > 0){
                    stopService(mpService)
                    position -= 1
                    mpService = Intent(this, MediaPlayerService::class.java)
                    mpService.putExtra("position", position)
                    startService(mpService)
                }
            }
        }
    }

    private fun setupPermissions(context: Context) {
        val permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission", "Permission to record denied")
            makeRequest(context)
        }
    }

    private fun makeRequest(context: Context) {
        ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 22)
    }
}
