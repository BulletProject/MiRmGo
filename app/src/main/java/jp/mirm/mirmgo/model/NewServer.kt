package jp.mirm.mirmgo.model

object NewServer {

    const val GAMEMODE_SURVIVAL = 0
    const val GAMEMODE_CREATIVE = 1
    const val GAMEMODE_UNKNOWN = -1

    const val DIFFICULTY_PEACEFUL = 0
    const val DIFFICULTY_EASY = 1
    const val DIFFICULTY_NORMAL = 2
    const val DIFFICULTY_HARD = 3
    const val DIFFICULTY_UNKNOWN = -1

    var serverId: String? = null
    var password: String? = null
    var gameMode = GAMEMODE_UNKNOWN
    var difficulty = DIFFICULTY_UNKNOWN
    var accepted = false

    fun getGameModeAsString() = when (gameMode) {
        GAMEMODE_SURVIVAL -> "survival"
        GAMEMODE_CREATIVE -> "creative"
        else -> "unknown"
    }

    fun getDifficultyAsString() = when (difficulty) {
        DIFFICULTY_PEACEFUL -> "peaceful"
        DIFFICULTY_EASY -> "easy"
        DIFFICULTY_NORMAL -> "normal"
        DIFFICULTY_HARD -> "hard"
        else -> "unknown"
    }

    fun reset() {
        serverId = null
        password = null
        gameMode = GAMEMODE_UNKNOWN
        difficulty = DIFFICULTY_UNKNOWN
        accepted = false
    }

    fun canCreateServer() = gameMode != GAMEMODE_UNKNOWN && difficulty != DIFFICULTY_UNKNOWN && accepted
}