package jp.mirm.mirmgo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.stetho.Stetho
import com.google.android.gms.ads.MobileAds
import jp.mirm.mirmgo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build())

        MobileAds.initialize(this, getString(R.string.admob_appid_test))
    }

}
