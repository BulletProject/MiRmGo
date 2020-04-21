package jp.mirm.mirmgo.common.exception

import java.lang.RuntimeException

class MissingRequestException(val errorCode: Int) : RuntimeException() {
    companion object {
        const val CODE_UNKNOWN = -1
    }
}