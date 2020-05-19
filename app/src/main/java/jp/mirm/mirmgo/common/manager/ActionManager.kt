package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.ActionResponse
import kotlinx.coroutines.*

class ActionManager : BaseManager<ActionManager>() {

    private var onSuccess: (action: ActionResponse) -> Unit = {}

    fun doAction(action: String) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

            withContext(Dispatchers.IO) {
                MiRmAPI.action(action)

            }.let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (action: ActionResponse) -> Unit): ActionManager {
        this.onSuccess = func
        return this
    }

}