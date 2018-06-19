package br.edu.unifor.smartmusic.adapter

import android.content.Context
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.edu.unifor.smartmusic.R
import br.edu.unifor.smartmusic.activity.MainActivity
import br.edu.unifor.smartmusic.entity.Music
import br.edu.unifor.smartmusic.services.MediaPlayerService

class MusicAdapter(val context: Context, val musics: List<Music>, val activity: MainActivity) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(itemView: View, activity: MainActivity, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mNameMusic: TextView
        val mAutorMusic: TextView
        val mPlaybtn: FloatingActionButton
        val mPausebtn: FloatingActionButton
        val mpService: Intent

        var id: Int

        init {
            mNameMusic = itemView.findViewById(R.id.music_name)
            mAutorMusic = itemView.findViewById(R.id.autor_music)
            mPlaybtn = activity.findViewById(R.id.play_btn)
            mPausebtn = activity.findViewById(R.id.pause_btn)
            mpService = Intent(context, MediaPlayerService::class.java)

            id = 0

            itemView.setOnClickListener(this)

            mPlaybtn.setOnClickListener(this)
            mPausebtn.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when(v.id){
                R.id.play_btn -> {
                    context.startService(mpService)
                }
                R.id.pause_btn ->{
                    context.startService(mpService)
                }
                else -> {
                    context.startService(mpService)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder{
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_list_music, parent, false)
        return MusicViewHolder(itemView, activity, context)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.mNameMusic.text = musics[position].nome
        holder.mAutorMusic.text = musics[position].autor
        holder.id = musics[position]._id
    }

}