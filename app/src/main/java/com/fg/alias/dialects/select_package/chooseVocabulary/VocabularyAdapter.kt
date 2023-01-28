package com.fg.alias.dialects.select_package.chooseVocabulary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R
import com.fg.alias.dialects.utils.Vocabulary
import com.fg.alias.dialects.utils.getVocabularyCount

class VocabularyAdapter(private val onVocabularySelected: ChooseVocabularyDialog.OnVocabularySelected) : RecyclerView
.Adapter<VocabularyAdapter.TeamViewHolder>() {

    private val regions = ArrayList<Vocabulary>()

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRegionName: TextView = itemView.findViewById(R.id.tvRegionName)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary, parent, false)
        return TeamViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.tvRegionName.text = regions[position].localise
        holder.tvCount.text = getVocabularyCount(regions[position])
        holder.itemView.setOnClickListener {
            onVocabularySelected.onVocabularySelected(regions[position]) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addVocabularies(regions: ArrayList<Vocabulary>){
        this.regions.addAll(regions)
        notifyDataSetChanged()
    }

    override fun getItemCount() = regions.size
}