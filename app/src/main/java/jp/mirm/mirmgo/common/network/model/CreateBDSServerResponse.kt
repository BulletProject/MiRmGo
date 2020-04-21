package jp.mirm.mirmgo.common.network.model

data class CreateBDSServerResponse(
    val serverId: String?,
    val created: Boolean,
    val status: String,
    val statusCode: Int
) {

    companion object {
        const val STATUS_SUCCESS = "Success"
        const val STATUS_FAILED_ERROR = "Error"
        const val STATUS_FAILED_INVALID_SERVERID = "Invalid ServerId."
        const val STATUS_FAILED_INVALID_PASSWORD = "Invalid Password."
        const val STATUS_FAILED_ALREADY_EXISTS = "Already exists."

        const val CODE_SUCCESS = 0
        const val CODE_FAILED = 1
        const val CODE_INVALID_SERVERID = 2
        const val CODE_INVALID_PASSWORD = 3
        const val CODE_ALREADY_EXISTS = 4
    }

    override fun toString(): String {
        return "CreateBDSServerResponse(serverId=$serverId, created=$created, status='$status', statusCode=$statusCode)"
    }


}