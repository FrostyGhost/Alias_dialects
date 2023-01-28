package com.fg.alias.dialects.game.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fg.alias.R
import com.fg.alias.dialects.utils.Word

class TeamsResultAdapter(
    private val teamsList: ArrayList<Word>,
    private val listener: OnSwitchClicked
) : RecyclerView
.Adapter<TeamsResultAdapter.TeamViewHolder>() {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val switch: CheckBox = itemView.findViewById(R.id.checkBox)
        val tvTeamName: TextView = itemView.findViewById(R.id.tvTeamName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team_result, parent, false)
        return TeamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.tvTeamName.text = teamsList[position].word
        holder.tvDescription.text = teamsList[position].description
        holder.switch.isChecked = teamsList[position].isCorrect

        holder.switch.setOnClickListener {
            listener.onSwitchClicked(!teamsList[position].isCorrect)
            teamsList[position].isCorrect = !teamsList[position].isCorrect
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = teamsList.size

    interface OnSwitchClicked{
        fun onSwitchClicked(isChecked: Boolean)
    }
}