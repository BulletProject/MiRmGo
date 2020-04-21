package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.CommandResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SendCommandManager : BaseManager<SendCommandManager>() {

    private var onSuccess: (commandResponse: CommandResponse) -> Unit = {}

    fun doSend(command: String) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.sendCommand(command)

        }.await().let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (commandResponse: CommandResponse) -> Unit): SendCommandManager {
        this.onSuccess = func
        return this
    }

}