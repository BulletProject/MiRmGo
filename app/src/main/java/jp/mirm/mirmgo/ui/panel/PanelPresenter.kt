package jp.mirm.mirmgo.ui.panel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.dialog.CustomizedDialog
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.util.Preferences
import kotlinx.coroutines.*

class PanelPresenter(private val fragment: PanelFragment) : AbstractPresenter() {

    private var time = 0
    private var flag = true
    private var started = false

    fun init() {
        if (!started) {
            GlobalScope.launch(Dispatchers.Main) {
                started = true

                while (flag) {
                    fragment.setProgressBarValue(time, 600)
                    fragment.setTime("$time/600分")
                    delay(60000)
                    time--
                }

                started = false
            }
        }
    }

    fun setTime(time: Int) {
        this.time = time
    }

    fun onPopupMenuClick(id: Int) {
        when (id) {
            R.id.popupOfficialSite -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_SITE))
                MyApplication.getApplication().startActivity(intent)
            }

            R.id.popupLogout -> {
                logout()
            }
        }
    }

    private fun logout() = GlobalScope.launch(Dispatchers.Main) {
        val dialog = CustomizedDialog.newInstance()
        dialog.arguments = bundleOf("text" to MyApplication.getString(R.string.panel_logout))
        dialog.show(
            fragment.activity?.supportFragmentManager ?: fragment.fragmentManager
            ?: fragment.requireFragmentManager(), "logging_out"
        )

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.logout()

        }.await().let {
            dialog.dismiss()
            if (it) {
                Preferences.setPassword(null)
                Preferences.setServerId(null)

                changeFragment(
                    fragment.activity?.supportFragmentManager ?: fragment.fragmentManager
                    ?: fragment.requireFragmentManager(), LoginFragment.newInstance()
                )
            } else {
                fragment.showSnackbar(R.string.panel_logout_error)
            }
        }
    }

    fun onPause() {
        flag = false
    }

}