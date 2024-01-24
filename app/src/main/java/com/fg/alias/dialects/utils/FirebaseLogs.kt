package com.fg.alias.dialects.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseLogs {
    private var instance: FirebaseLogs? = null
    private val firebaseAnalytics = Firebase.analytics

    init {
        if (instance == null){
            instance = FirebaseLogs
        }
    }

    fun customEvent(key: String, stringEvent: String){
        val bundle = Bundle()
        bundle.putString(key, stringEvent)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)
    }

    const val SUPPORT_BUTTON_CLICKED = "support button clicked"
    const val ON_BANNER_CLICKED = "on banner clicked"
    const val ON_GAME_END = "game over"
}