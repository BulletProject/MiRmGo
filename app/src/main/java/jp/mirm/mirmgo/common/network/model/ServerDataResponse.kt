package jp.mirm.mirmgo.common.network.model

data class ServerDataResponse(
    val id: String,
    val ip: String,
    val port: Int,
    val time: Int,
    val type: String,
    val serverStatus: Boolean
) {
    companion object {
        const val TYPE_PMMP = "pmmp"
        const val TYPE_BDS = "beof"
        const val TYPE_CUBERITE = "cuberite"
    }

    override fun toString(): String {
        return "ServerDataResponse(id='$id', ip='$ip', port=$port, time=$time, type='$type', serverStatus=$serverStatus)"
    }

}