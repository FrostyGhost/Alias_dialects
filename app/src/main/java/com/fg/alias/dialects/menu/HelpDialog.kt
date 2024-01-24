package com.fg.alias.dialects.menu

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.fg.alias.dialects.utils.FirebaseLogs
import com.fg.alias.dialects.utils.FirebaseLogs.SUPPORT_BUTTON_CLICKED
import com.fg.alias.dialects.utils.PrefUtils
import com.fg.alias.dialects.utils.PrefUtils.Companion.REMOVE_CONFIG_IS_ADDS
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.dialog_choose_vocabulary.*
import kotlinx.android.synthetic.main.dialog_help.tvSupport

class HelpDialog(
    private val listener: OnHelpDialogListener
) :
    DialogFragment(com.fg.alias.R.layout.dialog_help){

    override fun onStart() {
        super.onStart()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
        setupSupportButton()
    }

    private fun setupClickListener() {
        tvSupport.setOnClickListener {
            FirebaseLogs.customEvent(FirebaseAnalytics.Event.SELECT_ITEM, SUPPORT_BUTTON_CLICKED)
            listener.onSupportClicked() }
    }

    private fun setupSupportButton(){
        if (PrefUtils.with(requireContext()).getBoolean(REMOVE_CONFIG_IS_ADDS, false)){
            tvSupport.visibility = View.VISIBLE
        }
    }

    fun

    interface OnHelpDialogListener{
        fun onSupportClicked()
    }
}