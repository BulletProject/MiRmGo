package jp.mirm.mirmgo.common.network

import com.google.gson.Gson
import jp.mirm.mirmgo.common.exception.MissingRequestException
import okhttp3.*
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpCookie

object Http {

    private val client = OkHttpClient()
    private var cookie: CookieManager
    private var cookies = mutableListOf<String>()

    init {
        if (CookieHandler.getDefault() == null) {
            cookie = CookieManager()
            CookieHandler.setDefault(cookie)
        } else {
            cookie = CookieHandler.getDefault() as CookieManager
        }
    }

    fun post_X_WWW_URL_FORM_ENCODED(url: String, data: Map<String, String>): String? {
        val postData = data.let {
            var str = ""
            it.forEach {
                str += "${it.key}=${it.value}&"
            }
            str.removeSuffix("&")
        }

        val cookieStr = cookies.let {
            var str = ""
            it.forEach {
                str += "${it}; "
            }
            str.removeSuffix("; ")
        }

        val requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData)
        val request = Request.Builder()
            .url(url)
            .header("Content-Length", postData.length.toString())
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Referer", URLHolder.URL_LOGIN)
            .header("Host", URLHolder.HOST)
            .header("Origin", "${URLHolder.PROTOCOL}${URLHolder.HOST}")
            .header("Cookie", cookieStr)
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw MissingRequestException()
        return response.body()?.string()
    }

    fun post(url: String, data: Map<String, String>, type: String = "application/json; charset=UTF-8"): String? {
        val requestBody = RequestBody.create(
            MediaType.parse(type),
            Gson().toJson(data)
        )
        val request = Request.Builder().url(url).post(requestBody).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw MissingRequestException()
        return response.body()?.string()
    }

    fun get(url: String, data: Map<String, String> = mapOf()): String? {
        var postUrl = url

        if (data.isNotEmpty()) {
            postUrl += "?"
            data.forEach {
                postUrl += "${it.key}=${it.value}&"
            }
            postUrl = postUrl.removeSuffix("&")
        }

        val request = Request.Builder().url(postUrl).build()
        val response = client.newCall(request).execute()

        response.headers().values("Set-Cookie").forEach {
            setCookies(it)
        }

        if (!response.isSuccessful) throw MissingRequestException()
        return response.body()?.string()
    }

    fun setCookies(cookies: String) {
        HttpCookie.parse(cookies).forEach {
            this.cookies.add("${it.name}=${it.value}")
        }
    }

    fun reset() {
        cookies = mutableListOf()
        cookie = CookieManager()
        CookieHandler.setDefault(cookie)
    }

}