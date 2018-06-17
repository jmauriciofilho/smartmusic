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
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.view.View
import br.edu.unifor.smartmusic.util.PlayerMusic


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mMusics: RecyclerView

    private lateinit var mRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewLayoutManager: LinearLayoutManager

    private lateinit var musics: MutableList<Music>

    private lateinit var player: PlayerMusic

    private lateinit var mPlayBtn: FloatingActionButton
    private lateinit var mPauseBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions(this)

        musics = getMp3Songs(this).toMutableList()

        mRecyclerViewAdapter = MusicAdapter(this, musics)
        mRecyclerViewLayoutManager = LinearLayoutManager(this)

        mMusics = findViewById<RecyclerView>(R.id.recycler_view_musics).apply {
            setHasFixedSize(false)
            layoutManager = mRecyclerViewLayoutManager
            adapter = mRecyclerViewAdapter
        }

        mPlayBtn = findViewById(R.id.play_btn)
        mPlayBtn.setOnClickListener(this)

        mPauseBtn = findViewById(R.id.pause_btn)
        mPauseBtn.setOnClickListener(this)

        player = PlayerMusic(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.play_btn -> {
                player.play()
            }

            R.id.pause_btn -> {
                player.pause()
            }
        }
    }

    private fun getMp3Songs(ctx: Context): List<Music> {
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
