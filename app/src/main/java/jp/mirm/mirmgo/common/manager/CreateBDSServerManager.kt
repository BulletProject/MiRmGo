package jp.mirm.mirmgo.common.manager

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.AbstractResponse
import jp.mirm.mirmgo.common.network.model.CreateBDSServerResponse
import jp.mirm.mirmgo.model.NewServer
import kotlinx.coroutines.*

class CreateBDSServerManager : BaseManager<CreateBDSServerManager>() {

    private var onSuccess: (response: CreateBDSServerResponse) -> Unit = {}
    private var onAlreadyExists: (response: CreateBDSServerResponse) -> Unit = {}
    private var onFailed: (response: CreateBDSServerResponse) -> Unit = {}
    private var onInvalidPassword: (response: CreateBDSServerResponse) -> Unit = {}
    private var onInvalidServerId: (response: CreateBDSServerResponse) -> Unit = {}

    fun create(server: NewServer) = GlobalScope.launch(Dispatchers.Main) {
        onInitialize.invoke()

        withContext(Dispatchers.IO) {
            MiRmAPI.createBDSServer(server.serverId!!, server.password!!, server.getGameModeAsString(), server.getDifficultyAsString(), "operator")

        }.let {
            if (it.apiStatusCode != AbstractResponse.STATUS_SUCCEEDED) {
                onNotSucceeded(it.apiStatusCode!!)
            } else {
                when (it.statusCode) {
                    CreateBDSServerResponse.CODE_ALREADY_EXISTS -> onAlreadyExists.invoke(it)
                    CreateBDSServerResponse.CODE_FAILED -> onFailed.invoke(it)
                    CreateBDSServerResponse.CODE_INVALID_PASSWORD -> onInvalidPassword.invoke(it)
                    CreateBDSServerResponse.CODE_INVALID_SERVERID -> onInvalidServerId.invoke(it)
                    else -> onSuccess.invoke(it)
                }
            }
            onFinish.invoke()
        }
    }

    fun onSuccess(func: (response: CreateBDSServerResponse) -> Unit): CreateBDSServerManager {
        this.onSuccess = func
        return this
    }

    fun onAlreadyExists(func: (response: CreateBDSServerResponse) -> Unit): CreateBDSServerManager {
        this.onAlreadyExists = func
        return this
    }

    fun onFailed(func: (response: CreateBDSServerResponse) -> Unit): CreateBDSServerManager {
        this.onFailed = func
        return this
    }

    fun onInvalidPassword(func: (response: CreateBDSServerResponse) -> Unit): CreateBDSServerManager {
        this.onInvalidPassword = func
        return this
    }

    fun onInvalidServerId(func: (response: CreateBDSServerResponse) -> Unit): CreateBDSServerManager {
        this.onInvalidServerId = func
        return this
    }

}