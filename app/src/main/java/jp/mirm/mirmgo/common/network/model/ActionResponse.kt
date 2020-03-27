package jp.mirm.mirmgo.common.network.model

data class ActionResponse(
    val action: String,
    val status: String,
    val couldExecute: Boolean,
    val statusCode: Int
) {
    companion object {
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
        const val ACTION_REATART = "restart"
        const val ACTION_FORCE_STOP = "forcestop"
        const val ACTION_DELETE = "delete"
    }
}