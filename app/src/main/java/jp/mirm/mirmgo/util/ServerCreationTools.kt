package jp.mirm.mirmgo.util

import org.apache.commons.lang3.RandomStringUtils

object ServerCreationTools {

    private const val NUMBERS_LENGTH = 4
    private const val ALPHABETS_LENGTH = 4

    fun buildServerId(): String {
        return "MiRm" +
                RandomStringUtils.randomNumeric(NUMBERS_LENGTH) +
                RandomStringUtils.randomAlphanumeric(ALPHABETS_LENGTH)
    }

    fun buildPassword(): String {
        return PasswordManager.encrypt(RandomStringUtils.random(12, (('A'..'Z') + ('a'..'z') + ('0'..'9')).joinToString("") + "!%&#@_$"))
    }

}