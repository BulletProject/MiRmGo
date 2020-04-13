package jp.mirm.mirmgo

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.ActionResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HttpTest {
    @Test
    fun loginTest() {
        assertEquals("OK: Login", MiRmAPI.login("mirmtest", "tsubaki394"), 0)
        println(MiRmAPI.getServerData())
    }

    @Test
    fun termsTest() {
        println(MiRmAPI.getTerms())
    }
}
