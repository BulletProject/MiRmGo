package jp.mirm.mirmgo.model

data class NewServer(
    val serverId: String,
    val password: String,
    val gamemode: Int
) {
    companion object {
        const val GAMEMODE_SURVIVAL = 0
        const val GAMEMODE_CREATIVE = 1
        const val GAMEMODE_UNKNOWN = -1
    }
}