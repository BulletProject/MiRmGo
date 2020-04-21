package jp.mirm.mirmgo.common.network.model

data class LogoutResponse(
    val couldLogout: Boolean,
    private val apiStatus: Int?
) : AbstractResponse(apiStatus)