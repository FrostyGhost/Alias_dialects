package com.fg.alias.dialects.game.winer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R
import com.fg.alias.dialects.utils.Team
import com.fg.alias.dialects.utils.Word

class WinnerResultAdapter(
    private val teamsList: ArrayList<Team>
) : RecyclerView
.Adapter<WinnerResultAdapter.TeamViewHolder>() {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeamName: TextView = itemView.findViewById(R.id.tvTeamName)
        val tvScore: TextView = itemView.findViewById(R.id.tvScore)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_win_result, parent, false)
        return TeamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.tvTeamName.text = teamsList[position].name
        holder.tvScore.text = teamsList[position].score.toString()
    }

    override fun getItemCount() = teamsList.size
}