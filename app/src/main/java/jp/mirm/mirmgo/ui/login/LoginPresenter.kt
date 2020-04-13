package jp.mirm.mirmgo.ui.login

import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginPresenter(private val fragment: LoginFragment) : AbstractPresenter() {

    fun onTryLoginButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        fragment.setTryLoginButtonVisiblitity(false)
        fragment.setProgressBarVisibility(true)
        fragment.setErrorTextViewVisibility(false)

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.login(fragment.getServerId(), fragment.getPassword())

        }.await().let {
            when (it) {
                MiRmAPI.LOGIN_STATUS_SUCCEEDED -> {
                    onLoginSucceeded()
                    return@let
                }

                MiRmAPI.LOGIN_STATUS_FAILED -> {
                    fragment.setErrorTextViewText(R.string.login_e_failed)
                }

                MiRmAPI.LOGIN_STATUS_ERROR -> {
                    fragment.setErrorTextViewText(R.string.login_e_error)
                }

                else -> { return@let }
            }
            fragment.setErrorTextViewVisibility(true)
            fragment.setProgressBarVisibility(false)
            fragment.setTryLoginButtonVisiblitity(true)
        }
    }

    fun onBackButtonClick() {
        changeFragment(fragment.activity!!.supportFragmentManager, MainMenuFragment.newInstance())
    }

    private fun onLoginSucceeded() = GlobalScope.launch(Dispatchers.Main) {
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.getServerData()

        }.await().let {
            if (it.type != ServerDataResponse.TYPE_BDS) {
                onNotBDSServer()
                return@let
            }

            changeFragment(fragment.activity!!.supportFragmentManager, PanelFragment.newInstance())
        }
    }

    private fun onNotBDSServer() = GlobalScope.launch(Dispatchers.Main) {
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.logout()

        }.await().let {
            fragment.setErrorTextViewText(R.string.login_e_not_supported)
            fragment.setErrorTextViewVisibility(true)
            fragment.setProgressBarVisibility(false)
            fragment.setTryLoginButtonVisiblitity(true)
        }
    }
}