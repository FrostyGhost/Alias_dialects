package com.fg.alias.dialects.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fg.alias.R
import com.fg.alias.dialects.game.GameStateViewModel
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment(R.layout.fragment_menu){

    private val viewModel: GameStateViewModel by lazy {
        ViewModelProvider(requireActivity()).get(GameStateViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        viewModel.clear()

//        val sampleText: String =
//            requireContext().assets
//                .open("lviv_json.txt")
//                .bufferedReader().use {
//                    it.readText()
//                }
//        val q = getLvivWordArray(sampleText) as ArrayList<Word>
//        Log.e("QQ", "" + q.size )

//        generateJson(resources, requireContext())
    }

    private fun setupClickListeners() {
        tvRules.setOnClickListener { findNavController().navigate(R.id.rulesPackageFragment) }
        tvNewGame.setOnClickListener { findNavController().navigate(R.id.selectPackageFragment) }
        imgMore.setOnClickListener { findNavController().navigate(R.id.moreFragment) }
    }

}