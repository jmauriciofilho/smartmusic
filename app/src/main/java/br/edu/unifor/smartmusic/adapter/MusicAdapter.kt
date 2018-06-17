package br.edu.unifor.smartmusic.adapter

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.edu.unifor.smartmusic.R
import br.edu.unifor.smartmusic.activity.MainActivity
import br.edu.unifor.smartmusic.entity.Music
import br.edu.unifor.smartmusic.util.PlayerMusic

class MusicAdapter(val context: Context, val musics: List<Music>, val activity: MainActivity) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(itemView: View, val context: Context, activity: MainActivity) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mNameMusic: TextView
        val mAutorMusic: TextView
        val mPlaybtn: FloatingActionButton
        val mPausebtn: FloatingActionButton

        lateinit var music: Music

        init {
            mNameMusic = itemView.findViewById(R.id.music_name)
            mAutorMusic = itemView.findViewById(R.id.autor_music)
            mPlaybtn = activity.findViewById(R.id.play_btn)
            mPausebtn = activity.findViewById(R.id.pause_btn)

            itemView.setOnClickListener(this)

            mPlaybtn.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("text", v.id.toString())
            val player = PlayerMusic(context)
            when(v.id){
                R.id.play_btn -> {
                    player.play(music)
                }
                R.id.pause_btn ->{
                    player.pause(music)
                }
                else -> {
                    player.play(music)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder{
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_list_music, parent, false)
        return MusicViewHolder(itemView, context, activity)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.mNameMusic.text = musics[position].nome
        holder.mAutorMusic.text = musics[position].autor
        holder.music = musics[position]
    }
    
}