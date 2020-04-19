package jp.mirm.mirmgo

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

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
    }
}