package jp.mirm.mirmgo.common.network.model

data class LoginResponse(val status: Int, private val apiStatus: Int?) : AbstractResponse(apiStatus) {
    companion object {
        const val LOGIN_STATUS_SUCCEEDED = 0
        const val LOGIN_STATUS_FAILED = 1
        const val LOGIN_STATUS_DELETED_SERVER = 2
        const val LOGIN_STATUS_USER_DELETED = 3
    }
}