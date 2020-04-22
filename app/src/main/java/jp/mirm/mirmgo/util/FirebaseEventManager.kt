package jp.mirm.mirmgo.util

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R

object FirebaseEventManager {

    fun onLogin(method: String) {
        MyApplication.getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.LOGIN, bundleOf(
            "version" to MyApplication.getString(R.string.version),
            FirebaseAnalytics.Param.METHOD to method)
        )
    }

    fun onCreateServer() {
        MyApplication.getFirebaseAnalytics().logEvent("create", bundleOf("version" to MyApplication.getString(R.string.version)))
    }

}