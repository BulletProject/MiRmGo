package jp.mirm.mirmgo.common.network.model

data class AddFCMTokenResponse(
    val status: String,
    val statusCode: Int,
    private val appStatus: Int?
) : AbstractResponse(appStatus)