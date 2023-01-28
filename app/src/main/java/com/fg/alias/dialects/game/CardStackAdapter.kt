package com.fg.alias.dialects.game

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R
import com.fg.alias.dialects.utils.Word
import kotlin.random.Random

class CardStackAdapter(
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var wordsList: List<Word> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setWordList(list: ArrayList<Word>){
        wordsList = list
        notifyDataSetChanged()
    }

    fun getWordByPosition(position: Int): Word {
        return wordsList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordsList[position]
        holder.title.text = item.word
        holder.description.text = item.description
        holder.region.text = item.region
//        holder.imageBg.setBackgroundColor(Colord[Random.nextInt(0, Colord.size-1)])
    }

    val Colord = listOf<Int>(Color.GREEN, Color.RED, Color.CYAN, Color.WHITE, Color.DKGRAY, Color.MAGENTA)

    override fun getItemCount(): Int {
        return wordsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val region: TextView = view.findViewById(R.id.tvRegion)
        var imageBg: ImageView = view.findViewById(R.id.imageView)
    }

}