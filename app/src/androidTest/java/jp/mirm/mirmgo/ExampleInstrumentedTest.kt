package jp.mirm.mirmgo

import android.util.Base64
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.util.PasswordManager

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("jp.mirm.mirmgo", appContext.packageName)
    }

    @Test
    fun loginTest() {
        assertEquals("OK: Login", MiRmAPI.login("mirmtest", "tsubaki394"), 0)
    }

}
