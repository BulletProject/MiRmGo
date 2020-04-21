package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.LogoutResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LogoutManager : BaseManager<LogoutManager>() {

    private var onSuccess: (logoutResponse: LogoutResponse) -> Unit = {}

    fun doLogout() = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.logout()

        }.await().let {
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