package jp.mirm.mirmgo

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import jp.mirm.mirmgo.common.manager.AddFCMTokenManager
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
        Stetho.initialize(
            Stetho.newInitializerBuilder(application)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                .build())

        MobileAds.initialize(application, getString(R.string.admob_appid_test))
        FirebaseRemoteConfigManager.refresh()

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                AddFCMTokenManager()
                    .onInitialize { Log.d(getString(R.string.debug_flag), "Sending FCM Token...") }
                    .onSuccess { Log.d(getString(R.string.debug_flag), "Send FCM Token: ${it.status}(${it.statusCode})") }
                    .onError { Log.e(getString(R.string.debug_flag), "Send FCM Token: Error") }
                    .onOutOfService { Log.e(getString(R.string.debug_flag), "Send FCM Token: Out of service") }
                    .addFCMToken(it.result?.token ?: return@addOnCompleteListener)
            }
        }
    }
}