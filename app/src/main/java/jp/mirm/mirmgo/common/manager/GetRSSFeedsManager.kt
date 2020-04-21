package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetRSSFeedsManager : BaseManager<GetRSSFeedsManager>() {

    private var onSuccess: (feeds: MutableMap<String, String>) -> Unit = {}

    fun doGet() = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.getLatestRSSFeeds()

        }.await().let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                onSuccess.invoke(it.rssFeeds)
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (feeds: MutableMap<String, String>) -> Unit): GetRSSFeedsManager {
        this.onSuccess = func
        return this
    }

}