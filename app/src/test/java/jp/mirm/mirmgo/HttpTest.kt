package jp.mirm.mirmgo

import jp.mirm.mirmgo.common.network.MiRmAPI
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
        assertTrue("Logged In", MiRmAPI.login("mirmtest", "tsubaki394"))
        assertEquals(MiRmAPI.getServerData().id, "mirmtest")
    }
}
