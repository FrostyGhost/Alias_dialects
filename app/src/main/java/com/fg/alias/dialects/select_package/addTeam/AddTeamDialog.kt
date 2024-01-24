package com.fg.alias.dialects.select_package.addTeam

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.fg.alias.R
import kotlinx.android.synthetic.main.dialog_add_team.etAddCommandName
import kotlinx.android.synthetic.main.dialog_add_team.imgCheck
import kotlinx.android.synthetic.main.fragment_select_package.*


class AddTeamDialog(
    private val listener: OnTeamSelected,
    private val usedNames : ArrayList<String>
) : DialogFragment(R.layout.dialog_add_team){

    private lateinit var adapter: TeamsAdapter

    override fun onStart() {
        super.onStart()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupAddNewUser()
    }

    private fun setupAddNewUser(){
        imgCheck.setOnClickListener {
            if (!etAddCommandName.text.isNullOrBlank())  {
                listener.onNewTeamAdded(etAddCommandName.text.toString())
                dismiss()
            } else{
                Toast.makeText(requireContext(), "Введіть назву команди", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapter() {
        val namesList = ArrayList<String>()
        namesList.addAll(resources.getStringArray(R.array.teams_name))
        namesList.removeAll(usedNames.toSet())
        adapter = TeamsAdapter(namesList, false, object : OnTeamSelected {
            override fun onNewTeamAdded(name: String) {
                listener.onNewTeamAdded(name)
                dismiss()
            }
        })
        recyclerView.adapter = adapter
    }

    interface OnTeamSelected{
        fun onNewTeamAdded(name: String)
    }
}