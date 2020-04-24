package jp.mirm.mirmgo.common.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.cookie.PersistentCookieStore
import okhttp3.*
import okhttp3.internal.JavaNetCookieJar
import org.riversun.okhttp3.OkHttp3CookieHelper
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Http {

    private const val USER_AGENT = "MiRmGo/0.0.1"
    private val client: OkHttpClient
    private val headers: Headers

    init {
        val cookieHandler = CookieManager(PersistentCookieStore(MyApplication.getApplication()), CookiePolicy.ACCEPT_ALL)

        client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieHandler))
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        headers = Headers.Builder()
            .set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .set("User-agent", USER_AGENT)
            .set("Accept-Language", "ja,en-US;q=0.9,en;q=0.8")
            .set("Connection", "keep-alive")
            .set("Referer", URLHolder.URL_LOGIN)
            //.set("Host", URLHolder.HOST)
            .set("Origin", "${URLHolder.PROTOCOL}${URLHolder.HOST}")
            .build()
    }

    fun postXWwwFormUrlEncoded(url: String, data: Map<String, String>): String? {
        val postData = data.let {
            var str = ""
            it.forEach {
                str += "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}&"
            }
            str.removeSuffix("&")
        }

        val requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData)
        val request = Request.Builder()
            .url(url)
            .headers(headers)
            .header("Content-Length", postData.length.toString())
            .header("Content-Type", "application/x-www-form-urlencoded")
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw MissingRequestException(response.code())
        return response.body()?.string()
    }

    fun post(url: String, data: Map<String, String> = mapOf(), type: String = "application/x-www-form-urlencoded; charset=utf-8"): String? {
        val postData = data.let {
            var str = ""
            it.forEach {
                str += "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}&"
            }
            str.removeSuffix("&")
        }

        val requestBody = RequestBody.create(MediaType.parse(type), postData)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .headers(headers)
            .header("Content-Length", postData.length.toString())
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw MissingRequestException(response.code())
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

        val request = Request.Builder()
            .url(postUrl)
            .headers(headers)
            .build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw MissingRequestException(response.code())
        return response.body()?.string()
    }

}