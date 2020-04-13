package jp.mirm.mirmgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build())

        setContentView(R.layout.activity_main)
    }

}
