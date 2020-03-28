package jp.mirm.mirmgo

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.ActionResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HttpTest {
    @Test
    fun loginTest() {
        assertTrue("OK: Login", MiRmAPI.login("mirmtest", "tsubaki394"))
        assertEquals("OK: Get Server Data", MiRmAPI.getServerData().id, "mirmtest")
        assertTrue("OK: Start Server", MiRmAPI.action(ActionResponse.ACTION_START))
        runBlocking {
            delay(10000)
        }
        assertTrue("OK: Send Command", MiRmAPI.sendCommand("time set 0"))
        assertTrue("OK: Stop Server", MiRmAPI.action(ActionResponse.ACTION_STOP))
        assertTrue("OK: Force Stop Server", MiRmAPI.action(ActionResponse.ACTION_FORCE_STOP))
    }

    @Test
    fun extendTest() {
        assertTrue("OK: Login", MiRmAPI.login("mirmtest", "tsubaki394"))
        assertTrue("OK: Extend", MiRmAPI.extendNormally())
        assertTrue("OK: Logout", MiRmAPI.logout())
    }

    @Test

    fun logoutTest() {
        assertTrue("OK: Login", MiRmAPI.login("mirmtest", "tsubaki394"))
        assertTrue("OK: Logout", MiRmAPI.logout())
    }
}
