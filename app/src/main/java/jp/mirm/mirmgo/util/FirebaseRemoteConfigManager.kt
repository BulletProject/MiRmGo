package jp.mirm.mirmgo.util

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import jp.mirm.mirmgo.BuildConfig
import jp.mirm.mirmgo.R
import java.util.concurrent.TimeUnit

object FirebaseRemoteConfigManager {

    fun refresh() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.e("mirm_go", "Failed to activate to Firebase Remote config.")
                }
            }
    }

}