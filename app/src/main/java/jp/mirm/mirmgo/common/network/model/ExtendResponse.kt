package jp.mirm.mirmgo.common.network.model

data class ExtendResponse(
    val status: String,
    val statusCode: Int,
    private val apiStatus: Int?

) : AbstractResponse(apiStatus)