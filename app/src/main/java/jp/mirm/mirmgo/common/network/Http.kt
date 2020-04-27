package jp.mirm.mirmgo.common.network

import android.text.TextUtils
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.cookie.PersistentCookieStore
import okhttp3.*
import java.net.*
import java.nio.charset.StandardCharsets

object Http {

    private const val USER_AGENT = "MiRmGo/0.0.1"
    private val cookieManager: CookieManager
    private val cookies = mutableMapOf<String, String>()

    init {
        cookieManager = CookieManager(
            PersistentCookieStore(MyApplication.getApplication()),
            CookiePolicy.ACCEPT_ALL
        )
        CookieHandler.setDefault(cookieManager)
    }

    fun postXWwwFormUrlEncoded(url: String, data: Map<String, String>): String? {
        val postData = data.let {
            var str = ""
            it.forEach {
                str += "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}&"
            }
            str.removeSuffix("&")
        }

        var connection = URL(url).openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.setRequestProperty("Content-Length", postData.length.toString())
        setConnectionData(connection)

        connection.connect()

        writeContents(connection, postData)
        processCookies(connection)

        while (connection.responseCode == HttpURLConnection.HTTP_MOVED_TEMP
            || connection.responseCode == HttpURLConnection.HTTP_MOVED_PERM
            || connection.responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            val newURL = connection.getHeaderField("Location")
            val cookies = connection.getHeaderField("Set-Cookie")
            connection = URL(newURL).openConnection() as HttpURLConnection
            if (cookies != null) connection.setRequestProperty("Cookie", cookies)
            setConnectionData(connection)
            connection.connect()
        }
        /*
        if (connection.url.toString() == URLHolder.URL_AUTHENTICATE && connection.headerFields.get("Location")?.get(0) != null) {
            return get(connection.headerFields.get("Location")?.get(0)!!)
        }

         */

        return getResponseBody(connection)
    }

    fun post(
        url: String,
        data: Map<String, String> = mapOf(),
        type: String = "application/x-www-form-urlencoded; charset=utf-8"
    ): String? {
        val postData = data.let {
            var str = ""
            it.forEach {
                str += "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}&"
            }
            str.removeSuffix("&")
        }

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", type)
        connection.setRequestProperty("Content-Length", postData.length.toString())
        setConnectionData(connection)

        connection.connect()

        writeContents(connection, postData)
        processCookies(connection)

        println(connection.url.toString() + "::" + connection.responseCode)

        return getResponseBody(connection)
    }

    fun get(url: String, data: Map<String, String> = mapOf()): String? {
        var getUrl = url

        if (data.isNotEmpty()) {
            getUrl += "?"
            data.forEach {
                getUrl += "${it.key}=${it.value}&"
            }
            getUrl = getUrl.removeSuffix("&")
        }

        var connection = URL(getUrl).openConnection() as HttpURLConnection
        connection.doOutput = false
        connection.requestMethod = "GET"
        setConnectionData(connection)

        connection.connect()

        processCookies(connection)

        while (connection.responseCode == HttpURLConnection.HTTP_MOVED_TEMP
            || connection.responseCode == HttpURLConnection.HTTP_MOVED_PERM
            || connection.responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            val newURL = connection.getHeaderField("Location")
            val cookies = connection.getHeaderField("Set-Cookie")
            connection = URL(newURL).openConnection() as HttpURLConnection
            if (cookies != null) connection.setRequestProperty("Cookie", cookies)
            setConnectionData(connection)
            connection.connect()
        }

        return getResponseBody(connection)
    }

    private fun setConnectionData(connection: HttpURLConnection) {
        connection.instanceFollowRedirects = false
        connection.setRequestProperty(
            "Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
        )
        connection.setRequestProperty("User-agent", USER_AGENT)
        connection.setRequestProperty("Accept-Language", "ja,en-US;q=0.9,en;q=0.8")
        connection.setRequestProperty("Connection", "keep-alive")
        connection.setRequestProperty("Referer", URLHolder.URL_LOGIN)
        connection.setRequestProperty("Origin", "${URLHolder.PROTOCOL}${URLHolder.HOST}")

        var cookieData = ""
        cookies.forEach {
            cookieData += "${it.key}=${it.value};"
        }
        if (cookieData.isNotEmpty()) connection.setRequestProperty("Cookie", cookieData)
        /*
        if (cookieManager.cookieStore.cookies.size > 0) {
            connection.setRequestProperty(
                "Cookie",
                TextUtils.join(";", cookieManager.cookieStore.cookies)
            )
        }
         */
    }

    private fun processCookies(connection: HttpURLConnection) {
        println(connection.url.toString() + "::" + connection.responseCode)
        val fields = connection.headerFields
        val headers = fields["Set-Cookie"]
        headers?.forEach {
            HttpCookie.parse(it).forEach {
                cookies[it.name] = it.value
            }
        }
    }

    private fun writeContents(connection: HttpURLConnection, data: String) {
        connection.outputStream.bufferedWriter(StandardCharsets.UTF_8).use {
            it.write(data)
            it.flush()
        }
    }

    private fun getResponseBody(connection: HttpURLConnection): String {
        val buffer = StringBuffer()
        connection.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines {
            it.forEach {
                buffer.append(it)
            }
        }
        return buffer.toString()
    }

    private fun isRedirect(connection: HttpURLConnection): Boolean {
        return connection.responseCode.toString().startsWith("3")
    }

    private fun doRedirect(connection: HttpURLConnection): String? {
        val location = (connection.headerFields.get("Location") ?: return null).get(0)
        return get(location)
    }

}