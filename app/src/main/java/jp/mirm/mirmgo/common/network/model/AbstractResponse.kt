package jp.mirm.mirmgo.common.network.model

abstract class AbstractResponse(var apiStatusCode: Int?) {
    companion object {
        const val STATUS_SUCCEEDED = 0
        const val STATUS_ERROR = 1
        const val STATUS_NETWORK_ERROR = 2
        const val STATUS_OUT_OF_SERVICE = 3
    }
}