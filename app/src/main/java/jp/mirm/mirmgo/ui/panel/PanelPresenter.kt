package jp.mirm.mirmgo.ui.panel

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.panel.dialog.ExtendDialog
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
                    fragment.setProgressBarValue(time)
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
        val dialog = LoadingDialog.newInstance()
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

    fun onExtendButtonClick()  = GlobalScope.launch(Dispatchers.Main) {
        fragment.showSnackbar(R.string.loading)
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.getServerData()

        }.await().let {
            if (it == null) {
                fragment.showSnackbar(R.string.dialog_extend_error)
                return@let
            }

            if (it.time > 360) {
                fragment.showSnackbar(R.string.dialog_extend_error_time)
                return@let

            } else {
                val dialog = ExtendDialog.newInstance()
                dialog.show(fragment.activity!!.supportFragmentManager, "extend")
            }
        }
    }

    fun onPause() {
        flag = false
    }

}