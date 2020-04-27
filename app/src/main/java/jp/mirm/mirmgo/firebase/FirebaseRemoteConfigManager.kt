package jp.mirm.mirmgo.firebase

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import jp.mirm.mirmgo.BuildConfig
import jp.mirm.mirmgo.MyApplication
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
                    Log.e(MyApplication.getString(R.string.debug_flag), "Failed to activate to Firebase Remote config.")
                }
            }
    }

    fun isAllowedOtherServers(): Boolean = FirebaseRemoteConfig.getInstance().getBoolean("allow_other_servers")

}