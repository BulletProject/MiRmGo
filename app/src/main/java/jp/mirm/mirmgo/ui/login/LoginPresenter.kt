package jp.mirm.mirmgo.ui.login

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.AddFCMTokenManager
import jp.mirm.mirmgo.common.manager.GetServerDataManager
import jp.mirm.mirmgo.common.manager.LoginManager
import jp.mirm.mirmgo.common.manager.LogoutManager
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.firebase.FirebaseEventManager
import jp.mirm.mirmgo.firebase.FirebaseRemoteConfigManager
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
                if (!FirebaseRemoteConfigManager.isAllowedOtherServers() && it.type != ServerDataResponse.TYPE_BDS) {
                    onNotBDSServer()
                } else {
                    onFinalProcess()
                }
            }
            .doGet()
    }

    private fun onFinalProcess() {
        if (fragment.isSaveDataChecked()) {
            saveLoginData(fragment.getServerId(), fragment.getPassword())
        } else {
            Preferences.removeCurrentServer()
        }

        changeFragment(fragment.activity!!.supportFragmentManager, PanelFragment.getInstance())
        FirebaseEventManager.onLogin("self")

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                AddFCMTokenManager()
                    .onInitialize { Log.d(MyApplication.getString(R.string.debug_flag), "Sending FCM Token...") }
                    .onSuccess { Log.d(MyApplication.getString(R.string.debug_flag), "Send FCM Token: ${it.status}(${it.statusCode})") }
                    .onError { Log.e(MyApplication.getString(R.string.debug_flag), "Send FCM Token: Error") }
                    .onOutOfService { Log.e(MyApplication.getString(R.string.debug_flag), "Send FCM Token: Out of service") }
                    .addFCMToken(it.result?.token ?: return@addOnCompleteListener)
            }
        }
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
        Preferences.addServer(serverId, password)
        Preferences.setCurrentServer(serverId)
    }
}