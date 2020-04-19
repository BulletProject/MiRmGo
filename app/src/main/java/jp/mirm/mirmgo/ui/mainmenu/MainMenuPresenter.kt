package jp.mirm.mirmgo.ui.mainmenu

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerFragment
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.util.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainMenuPresenter(private val fragment: MainMenuFragment) : AbstractPresenter() {

    private val fragmentManager: FragmentManager = fragment.activity?.supportFragmentManager ?: fragment.fragmentManager ?:fragment.requireFragmentManager()

    fun init() {
        val serverId = Preferences.getServerId()
        val password = Preferences.getPassword()

        if (serverId != null && password != null) {
            tryLogin(serverId, password)
        }
    }

    fun onCreateServerClick() {
        changeFragment(fragmentManager, CreateServerFragment.newInstance())
    }

    fun onLoginClick() {
        changeFragment(fragmentManager, LoginFragment.newInstance())
    }

    fun onAboutButtonClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_ABOUT))
        MyApplication.getApplication().startActivity(intent)
    }

    private fun tryLogin(serverId: String, password: String) = GlobalScope.launch(Dispatchers.Main) {
        val dialog = LoadingDialog.newInstance()
        dialog.show(fragmentManager, "logging_in")

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.login(serverId, password)

        }.await().let {
            dialog.dismiss()
            when (it) {
                MiRmAPI.LOGIN_STATUS_SUCCEEDED -> {
                    changeFragment(fragmentManager, PanelFragment.getInstance())
                }
                else -> {
                    fragment.showLoginSnackbar(R.string.main_login_failed, Snackbar.LENGTH_SHORT)
                }
            }
        }
    }

}