package jp.mirm.mirmgo.ui.login

import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.GetServerDataManager
import jp.mirm.mirmgo.common.manager.LoginManager
import jp.mirm.mirmgo.common.manager.LogoutManager
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.firebase.FirebaseEventManager
import jp.mirm.mirmgo.util.Preferences

class LoginPresenter(private val fragment: LoginFragment) : AbstractPresenter() {

    fun onTryLoginButtonClick() {
        LoginManager()
            .onInitialize {
                fragment.setTryLoginButtonVisibility(false)
                fragment.setProgressBarVisibility(true)
                fragment.setErrorTextViewVisibility(false)
            }
            .onFinish {
                fragment.setErrorTextViewVisibility(true)
                fragment.setProgressBarVisibility(false)
                fragment.setTryLoginButtonVisibility(true)
            }
            .onLoginSuccess { onLoginSucceeded() }
            .onUserDeleted { fragment.setErrorTextViewText(R.string.main_login_user_deleted) }
            .onDeleted { fragment.setErrorTextViewText(R.string.main_login_deleted) }
            .onNetworkError { fragment.setErrorTextViewText(R.string.network_error) }
            .onError { fragment.setErrorTextViewText(R.string.error) }
            .doLogin(fragment.getServerId(), fragment.getPassword())
    }

    fun onBackButtonClick() {
        changeFragment(fragment.activity!!.supportFragmentManager, MainMenuFragment.newInstance())
    }

    private fun onLoginSucceeded() {
        GetServerDataManager()
            .onSuccess {
                if (!Preferences.isOtherServersAllowed() && it.type != ServerDataResponse.TYPE_BDS) {
                    onNotBDSServer()
                } else {
                    if (fragment.isSaveDataChecked()) saveLoginData(fragment.getServerId(), fragment.getPassword())
                    changeFragment(fragment.activity!!.supportFragmentManager, PanelFragment.getInstance())
                    FirebaseEventManager.onLogin("self")
                }
            }
            .doGet()
    }

    private fun onNotBDSServer() {
        LogoutManager()
            .onFinish {
                fragment.setErrorTextViewText(R.string.login_e_not_supported)
                fragment.setErrorTextViewVisibility(true)
                fragment.setProgressBarVisibility(false)
                fragment.setTryLoginButtonVisibility(true)
            }
            .doLogout()
    }

    private fun saveLoginData(serverId: String, password: String) {
        Preferences.setServerId(serverId)
        Preferences.setPassword(password)
    }
}