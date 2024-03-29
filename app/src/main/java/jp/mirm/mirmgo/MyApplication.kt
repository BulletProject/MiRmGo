package jp.mirm.mirmgo

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import jp.mirm.mirmgo.firebase.FirebaseRemoteConfigManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyApplication : Application() {

    companion object {
        private lateinit var application: MyApplication
        private lateinit var fireBaseAnalytics: FirebaseAnalytics

        fun getApplication(): MyApplication {
            return application
        }

        fun getString(id: Int): String {
            return getApplication().getString(id)
        }

        fun getFirebaseAnalytics() = fireBaseAnalytics
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        fireBaseAnalytics = FirebaseAnalytics.getInstance(getApplication())

        initialize()
    }

    private fun initialize() = GlobalScope.launch(Dispatchers.Default) {
        MobileAds.initialize(application, getString(R.string.admob_appid))
        FirebaseRemoteConfigManager.refresh()
    }
}