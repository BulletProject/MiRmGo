package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.model.AbstractResponse

open class BaseManager<T: BaseManager<T>> {

    protected var onInitialize: () -> Unit = {}
    protected var onFinish: () -> Unit = {}
    protected var onError: () -> Unit = {}
    protected var onNetworkError: () -> Unit = {}
    protected var onOutOfService: () -> Unit = {}

    fun onInitialize(func: () -> Unit): T {
        this.onInitialize = func
        return this as T
    }

    fun onFinish(func: () -> Unit): T {
        this.onFinish = func
        return this as T
    }

    fun onError(func: () -> Unit): T {
        this.onError = func
        return this as T
    }

    fun onNetworkError(func: () -> Unit): T {
        this.onNetworkError = func
        return this as T
    }

    fun onOutOfService(func: () -> Unit): T {
        this.onOutOfService = func
        return this as T
    }

    protected fun onNotSucceeded(apiStatusCode: Int) {
        when (apiStatusCode) {
            AbstractResponse.STATUS_OUT_OF_SERVICE -> onOutOfService.invoke()
            AbstractResponse.STATUS_ERROR -> onError.invoke()
            AbstractResponse.STATUS_NETWORK_ERROR -> onNetworkError.invoke()
        }
    }

}