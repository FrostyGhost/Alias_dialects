package com.fg.alias.dialects.game.winer

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.fg.alias.R
import com.fg.alias.dialects.game.result.ResultDialog
import com.fg.alias.dialects.utils.Team
import kotlinx.android.synthetic.main.dialog_win.*
import kotlinx.android.synthetic.main.fragment_select_package.recyclerView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


class WinnerDialog(
    private val teamList: ArrayList<Team>,
    private val winnerTeam: Team,
    private val listener: ResultDialog.OnDialogDismiss,
) : DialogFragment(R.layout.dialog_win){

    private lateinit var adapter: WinnerResultAdapter

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
        setupTitle()
        setupClickListener()
    }

    private fun setupClickListener() {
        tvOpenMenu.setOnClickListener { dismiss() }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTitle() {
        tvTitle.text = "Вітаємо переможців! \n ${winnerTeam.name}"
        konfettiView.start(Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            position = Position.Relative(0.5, 0.3),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
        ))
    }

    private fun setupAdapter() {
        adapter = WinnerResultAdapter(teamList)
        recyclerView.adapter = adapter
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onDismiss(0)
    }
}