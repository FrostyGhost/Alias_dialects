package com.fg.alias.dialects.select_package.addTeam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R

class TeamsAdapter(
    private val names: ArrayList<String>,
    private val isRemoveIconVisible: Boolean = true,
    private val listener: AddTeamDialog.OnTeamSelected? = null
) : RecyclerView
.Adapter<TeamsAdapter.TeamViewHolder>() {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeamName: TextView = itemView.findViewById(R.id.tvTeamName)
        val tvClose: TextView = itemView.findViewById(R.id.tvClose)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.tvTeamName.text = names[position]
        if (isRemoveIconVisible){
            holder.tvClose.visibility = View.VISIBLE
            holder.tvClose.setOnClickListener {
                names.removeAt(position)
                notifyDataSetChanged()
            }
        }else{
            holder.tvClose.visibility = View.GONE
        }
        holder.itemView.setOnClickListener { listener?.onNewTeamAdded(names[position]) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTeam(name: String){
        names.add(name)
        notifyDataSetChanged()
    }

    fun getTeamsList(): ArrayList<String> {
        return names
    }

    override fun getItemCount() = names.size
}