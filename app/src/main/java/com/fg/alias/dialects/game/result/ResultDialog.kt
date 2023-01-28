package com.fg.alias.dialects.game.result

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.fg.alias.R
import com.fg.alias.dialects.utils.Word
import kotlinx.android.synthetic.main.dialog_team_result.*
import kotlinx.android.synthetic.main.fragment_select_package.*
import kotlinx.android.synthetic.main.fragment_select_package.recyclerView


class ResultDialog(
    private val listener: OnDialogDismiss,
    private val wordsList: ArrayList<Word>,
    score: Int
) : DialogFragment(R.layout.dialog_team_result){

    private var currentScore = score
    private lateinit var adapter: TeamsResultAdapter

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
        setupScoreView(currentScore)
        setupClickListener()
    }

    private fun setupClickListener() {
        tvNext.setOnClickListener { dismiss() }
        tvAllTeamResult.setOnClickListener {

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupScoreView(score: Int) {
        tvTitle.text = "Рахунок за раунд: $score"
    }

    private fun setupAdapter() {
        adapter = TeamsResultAdapter(wordsList, object : TeamsResultAdapter.OnSwitchClicked {
            override fun onSwitchClicked(isChecked: Boolean) {
                if (isChecked){
                    currentScore+=1
                }else{
                    currentScore-=1
                }
                setupScoreView(currentScore)
            }
        })
        recyclerView.adapter = adapter
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onDismiss(currentScore)
    }

    interface OnDialogDismiss{
        fun onDismiss(score: Int)
    }
}