package jp.mirm.mirmgo.common.network.model

data class CommandResponse(
    val command: String,
    val status: String,
    val statusCode: Int
)