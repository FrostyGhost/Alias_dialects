package com.fg.alias.dialects.select_package.chooseVocabulary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R
import com.fg.alias.dialects.utils.VocabularyItem
import com.fg.alias.dialects.utils.getVocabularyCount

class VocabularyAdapter(private val onVocabularySelected: ChooseVocabularyDialog.OnVocabularySelected) : RecyclerView
.Adapter<VocabularyAdapter.TeamViewHolder>() {

    private val regions = ArrayList<VocabularyItem>()

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRegionName: TextView = itemView.findViewById(R.id.tvRegionName)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary, parent, false)
        return TeamViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.tvRegionName.text = regions[position].voc.localise
        holder.tvCount.text = getVocabularyCount(regions[position].voc)
        holder.itemView.setOnClickListener {
            regions[position].isSelected = !regions[position].isSelected
            changeBgColor(holder, position)
            onVocabularySelected.onVocabularySelected(regions[position].voc) }
    }

    private fun changeBgColor(holder: TeamViewHolder, position: Int) {
        if (regions[position].isSelected){
            holder.container.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
        }else{
            holder.container.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addVocabularies(regions: ArrayList<VocabularyItem>){
        this.regions.addAll(regions)
        notifyDataSetChanged()
    }

    override fun getItemCount() = regions.size
}