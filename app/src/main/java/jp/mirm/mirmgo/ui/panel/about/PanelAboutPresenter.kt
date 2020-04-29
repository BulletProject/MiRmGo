package jp.mirm.mirmgo.ui.panel.about

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.panel.dialog.DeleteDialog

class PanelAboutPresenter(private val fragment: PanelAboutFragment) {

    fun onTermsClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_TERMS))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onOSSClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_OSS))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onHPClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_SITE))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onDeleteClick() {
        val dialog = DeleteDialog.newInstance()
        dialog.show(fragment.activity!!.supportFragmentManager, "delete_cilck")
    }

}