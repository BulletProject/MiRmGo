package jp.mirm.mirmgo

import android.app.Application

class MyApplication : Application() {

    companion object {
        private lateinit var application: MyApplication
        fun getApplication(): MyApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}