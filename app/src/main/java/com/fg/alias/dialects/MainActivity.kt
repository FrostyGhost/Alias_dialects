package com.fg.alias.dialects

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fg.alias.R
import com.fg.alias.dialects.utils.PrefUtils
import com.google.android.gms.ads.MobileAds
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        checkRemoteConfig()
    }

    private fun checkRemoteConfig(){
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val settings = FirebaseRemoteConfigSettings.Builder()
        settings.minimumFetchIntervalInSeconds = 3600
        remoteConfig.setConfigSettingsAsync(settings.build())

        val isAddsVisible = remoteConfig.getBoolean("isAddsVisible")
        PrefUtils.with(this).save(PrefUtils.REMOVE_CONFIG_IS_ADDS, isAddsVisible)
    }
}