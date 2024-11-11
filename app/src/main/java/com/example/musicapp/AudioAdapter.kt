package com.example.musicapp

import android.app.Activity
import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AudioAdapter(val context:Activity, val dataList:List<Data>)
    :RecyclerView.Adapter<AudioAdapter.AudioViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.audio_item, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val currentData = dataList[position]
        val mediaPlayer = MediaPlayer.create(context,currentData.preview.toUri())
        holder.titleText.text= currentData.title
        holder.artistText.text=currentData.artist.name

        val color:Int = holder.cardView.cardBackgroundColor.defaultColor
//        holder.imageView1.s
        Picasso.get().load(currentData.album.cover).fit().into(holder.imageView1)

        holder.playBtn.setOnClickListener {
            holder.cardView.setCardBackgroundColor("#4CAF50".toColorInt())
            holder.playBtn.isVisible=false
            holder.pauseBtn.isVisible=true

            //updating globalPlayer
//            holder.tackTitle.text=currentData.title?:"ki"
//            Picasso.get().load(currentData.album.cover_small) .resize(64, 64).into()


            mediaPlayer.start()
        }

        holder.pauseBtn.setOnClickListener {
            holder.playBtn.isVisible=true
            holder.pauseBtn.isVisible=false
            holder.cardView.setCardBackgroundColor(color)
            mediaPlayer.pause()
        }
    }

    class AudioViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView1:ImageView=itemView.findViewById(R.id.imageView)
        val titleText:TextView=itemView.findViewById(R.id.title)
        val artistText:TextView=itemView.findViewById(R.id.artist)
        val cardView:CardView=itemView.findViewById(R.id.cardView)

        val playBtn:ImageButton = itemView.findViewById(R.id.playBtn)
//        val prevBtn:ImageButton=itemView.findViewById(R.id.prevBtn)
//        val nextBtn:ImageButton=itemView.findViewById(R.id.nextBtn)
        val pauseBtn:ImageButton=itemView.findViewById(R.id.pauseBtn)
//        val tackTitle :TextView= itemView.findViewById(R.id.trackTitle)
//        val thumbnail : ImageView=itemView.findViewById(R.id.thumbNailView)


    }
}