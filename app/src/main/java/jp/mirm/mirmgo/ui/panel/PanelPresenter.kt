package jp.mirm.mirmgo.ui.panel

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.GetServerDataManager
import jp.mirm.mirmgo.common.manager.LogoutManager
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

    private fun logout() {
        val dialog = LoadingDialog.newInstance()

        LogoutManager()
            .onSuccess {
                if (it.couldLogout) {
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
            .onInitialize {
                dialog.arguments = bundleOf("text" to MyApplication.getString(R.string.panel_logout))
                dialog.show(
                    fragment.activity?.supportFragmentManager ?: fragment.fragmentManager
                    ?: fragment.requireFragmentManager(), "logging_out"
                )
            }
            .onFinish { dialog.dismiss() }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .doLogout()
    }

    fun onExtendButtonClick() {
        GetServerDataManager()
            .onSuccess {
                if (it.time > 360) {
                    fragment.showSnackbar(R.string.dialog_extend_error_time)

                } else {
                    val dialog = ExtendDialog.newInstance()
                    dialog.show(fragment.activity!!.supportFragmentManager, "extend")
                }
            }
            .onInitialize { fragment.showSnackbar(R.string.loading) }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .doGet()
    }

    fun onPause() {
        flag = false
    }

}