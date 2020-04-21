package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetServerDataManager : BaseManager<GetServerDataManager>() {

    private var onSuccess: (data: ServerDataResponse) -> Unit = {}

    fun doGet() = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.getServerData()

        }.await().let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (data: ServerDataResponse) -> Unit): GetServerDataManager {
        this.onSuccess = func
        return this
    }
}