package jp.mirm.mirmgo

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.util.PasswordManager
import jp.mirm.mirmgo.util.ServerCreationTools
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

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

    @Test
    fun passwordTest() {
        fun a(value: String): String {
            val keySpec = SecretKeySpec("ana1sexmach1ne0".toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            return String(cipher.doFinal(Base64.getDecoder().decode(value)))
        }
        assertEquals(PasswordManager.decrypt(PasswordManager.encrypt("Analsex1919@sex")), a(PasswordManager.encrypt("Analsex1919@sex")))
    }

    @Test
    fun buildServerData() {
        assertEquals("", ServerCreationTools.buildPassword())
        assertEquals("", ServerCreationTools.buildServerId())
    }

}
