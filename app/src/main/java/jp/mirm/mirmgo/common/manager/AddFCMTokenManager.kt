package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.AddFCMTokenResponse
import kotlinx.coroutines.*

class AddFCMTokenManager : BaseManager<AddFCMTokenManager>() {

    private var onSuccess: (addFCMTokenResponse: AddFCMTokenResponse) -> Unit = {}

    fun addFCMToken(token: String) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        withContext(Dispatchers.IO) {
            MiRmAPI.addFCMToken(token)

        }.let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (addFCMTokenResponse: AddFCMTokenResponse) -> Unit): AddFCMTokenManager {
        this.onSuccess = func
        return this
    }

}