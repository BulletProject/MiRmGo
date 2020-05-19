package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.LoginResponse
import kotlinx.coroutines.*

class LoginManager : BaseManager<LoginManager>() {

    private var onLoginSuccess: () -> Unit = {}
    private var onLoginFailed: () -> Unit = {}
    private var onDeleted: () -> Unit = {}
    private var onUserDeleted: () -> Unit = {}

    fun doLogin(serverId: String, password: String) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        withContext(Dispatchers.IO) {
            MiRmAPI.login(serverId, password)

        }.let {
            if (it.status != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)

            } else {
                when (it.apiStatusCode) {
                    LoginResponse.LOGIN_STATUS_FAILED -> onLoginFailed.invoke()
                    LoginResponse.LOGIN_STATUS_SUCCEEDED -> onLoginSuccess.invoke()
                    LoginResponse.LOGIN_STATUS_USER_DELETED -> onUserDeleted.invoke()
                    LoginResponse.LOGIN_STATUS_DELETED_SERVER -> onDeleted.invoke()
                }
            }
            onFinish.invoke()
        }
    }

    fun onLoginSuccess(func: () -> Unit): LoginManager {
        this.onLoginSuccess = func
        return this
    }

    fun onLoginFailed(func: () -> Unit): LoginManager {
        this.onLoginFailed = func
        return this
    }

    fun onDeleted(func: () -> Unit): LoginManager {
        this.onDeleted = func
        return this
    }

    fun onUserDeleted(func: () -> Unit): LoginManager {
        this.onUserDeleted = func
        return this
    }

}