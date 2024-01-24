package com.fg.alias.dialects.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fg.alias.dialects.game.GameStateViewModel
import com.fg.alias.dialects.utils.FirebaseLogs
import com.fg.alias.dialects.utils.generateJson
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_menu.*
import com.fg.alias.R
import com.fg.alias.dialects.utils.FirebaseLogs.ON_BANNER_CLICKED
import com.fg.alias.dialects.utils.PrefUtils

class MenuFragment : Fragment(R.layout.fragment_menu){

    private var rewardedAd: RewardedAd? = null
    private val TAG = "QQ"
    private var isLoading = false

    private val viewModel: GameStateViewModel by lazy {
        ViewModelProvider(requireActivity()).get(GameStateViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupAds()
        viewModel.clear()
    }

    private fun setupAds(){
        if (PrefUtils.with(requireContext()).getBoolean(PrefUtils.REMOVE_CONFIG_IS_ADDS, false)){
            loadRewardedAd()
            MobileAds.initialize(requireActivity()) {}

            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            adView.adListener = object: AdListener() {
                override fun onAdClicked() {
                    FirebaseLogs.customEvent(FirebaseAnalytics.Event.SELECT_ITEM, "onMenu Banner clicked")
                }
            }
            adView.visibility = View.VISIBLE
        }else{
            adView.visibility = View.GONE
        }

        adView.setOnClickListener {
            FirebaseLogs.customEvent(FirebaseAnalytics.Event.SELECT_ITEM, ON_BANNER_CLICKED)
        }
    }

    private fun generateVocabularyJson(){
        generateJson(resources.getStringArray(R.array.pod), requireContext(), "Одеські говірки")
    }

    private fun loadRewardedAd() {
        if (rewardedAd == null) {
            isLoading = true
            val adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                requireContext(),
                "ca-app-pub-3940256099942544/5224354917",
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.message)
                        isLoading = false
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        rewardedAd = ad
                        isLoading = false
                    }
                }
            )
        }
    }

    private fun showRewardedVideo() {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        rewardedAd = null
                        loadRewardedAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        rewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }

            rewardedAd?.show(requireActivity()) { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
//                    addCoins(rewardAmount)
                Log.d("TAG", "User earned the reward. $rewardAmount // $rewardType")
            }
        }else{
            loadRewardedAd()
        }
    }

    private fun generateNewVocabulary(){
        tvRules.setOnClickListener {
            findNavController().navigate(R.id.rulesPackageFragment)

                //todo uncomment to generate new json
            generateVocabularyJson()
        }
    }

    private fun showHelpDialog(){
        val dialog = HelpDialog{
            showRewardedVideo()
        }
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun setupClickListeners() {
        tvNewGame.setOnClickListener { findNavController().navigate(R.id.selectPackageFragment) }
        imgMore.setOnClickListener {
//            findNavController().navigate(R.id.moreFragment)
//            showRewardedVideo()
            showHelpDialog()
        }
    }

}