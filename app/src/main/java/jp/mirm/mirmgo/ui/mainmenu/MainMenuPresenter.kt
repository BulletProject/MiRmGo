package jp.mirm.mirmgo.ui.mainmenu

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.LoginManager
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerFragment
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.firebase.FirebaseEventManager
import jp.mirm.mirmgo.util.Preferences

class MainMenuPresenter(private val fragment: MainMenuFragment) : AbstractPresenter() {

    private val fragmentManager: FragmentManager = fragment.activity?.supportFragmentManager ?: fragment.fragmentManager ?:fragment.requireFragmentManager()

    fun init() {
        val currentServerId = Preferences.getCurrentServerId()
        if (currentServerId != null) {
            val password = Preferences.getDecryptedPassword(currentServerId) ?: return
            tryLogin(currentServerId, password)
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
        fragment.activity!!.startActivity(intent)
    }

    private fun tryLogin(serverId: String, password: String) {
        val dialog = LoadingDialog.newInstance()
        LoginManager()
            .onLoginSuccess {
                FirebaseEventManager.onLogin("auto_login")
                changeFragment(fragmentManager, PanelFragment.getInstance())
            }
            .onDeleted { fragment.showSnackbar(R.string.main_login_deleted) }
            .onUserDeleted { fragment.showSnackbar(R.string.main_login_user_deleted) }
            .onLoginFailed { fragment.showSnackbar(R.string.main_login_failed) }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onInitialize { dialog.show(fragmentManager, "logging_in") }
            .onFinish { dialog.dismiss() }
            .doLogin(serverId, password)
    }

}