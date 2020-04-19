package jp.mirm.mirmgo.ui.panel.about

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.common.network.URLHolder

class PanelAboutPresenter {

    fun onTermsClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_TERMS))
        MyApplication.getApplication().startActivity(intent)
    }

    fun onOSSClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_OSS))
        MyApplication.getApplication().startActivity(intent)
    }

    fun onHPClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_SITE))
        MyApplication.getApplication().startActivity(intent)
    }

}