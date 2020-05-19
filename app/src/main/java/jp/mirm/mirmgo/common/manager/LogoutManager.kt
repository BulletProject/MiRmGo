package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.LogoutResponse
import kotlinx.coroutines.*

class LogoutManager : BaseManager<LogoutManager>() {

    private var onSuccess: (logoutResponse: LogoutResponse) -> Unit = {}

    fun doLogout() = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        withContext(Dispatchers.IO) {
            MiRmAPI.logout()

        }.let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (logoutResponse: LogoutResponse) -> Unit): LogoutManager {
        this.onSuccess = func
        return this
    }

}