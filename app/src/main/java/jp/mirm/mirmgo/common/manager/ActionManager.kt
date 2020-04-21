package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.ActionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ActionManager : BaseManager<ActionManager>() {

    private var onSuccess: (action: ActionResponse) -> Unit = {}

    fun doAction(action: String) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.action(action)

        }.await().let {
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