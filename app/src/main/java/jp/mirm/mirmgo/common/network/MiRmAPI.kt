package jp.mirm.mirmgo.common.network

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rometools.rome.io.SyndFeedInput
import jp.mirm.mirmgo.common.exception.CouldNotExtendException
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.common.network.model.CommandResponse
import jp.mirm.mirmgo.common.network.model.ExtendResponse
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import org.jsoup.Jsoup
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.Exception
import java.net.URLEncoder

object MiRmAPI {

    private val gson = Gson()
    var loggedIn = false
    var serverId = ""

    const val LOGIN_STATUS_SUCCEEDED = 0
    const val LOGIN_STATUS_FAILED = 1
    const val LOGIN_STATUS_ERROR = 2

    fun login(serverId: String, password: String): Int {
        try {
            val response = Http.postXWwwFormUrlEncoded(
                URLHolder.URL_AUTHENTICATE, mapOf(
                    "serverId" to URLEncoder.encode(serverId),
                    "password" to URLEncoder.encode(password),
                    "_csrf" to getCsrf()
                )
            ) ?: return LOGIN_STATUS_ERROR

            return response.contains("MiRm | コントロールパネル").let {
                if (it) {
                    this.loggedIn = true
                    this.serverId = serverId
                    LOGIN_STATUS_SUCCEEDED
                } else {
                    LOGIN_STATUS_FAILED
                }
            }

        } catch (e: Exception) {
        }

        return LOGIN_STATUS_ERROR
    }

    fun getServerData(): ServerDataResponse? {
        try {
            val json = Http.get(URLHolder.URL_SERVER_DATA) ?: throw MissingRequestException()
            return gson.fromJson(json, ServerDataResponse::class.java)

        } catch (e: Exception) {
            return null
        }
    }

    fun sendCommand(command: String): Boolean {
        try {
            val json =
                Http.postXWwwFormUrlEncoded(URLHolder.URL_SEND_COMMAND, mapOf("command" to command))
                    ?: throw MissingRequestException()
            val response = gson.fromJson(json, CommandResponse::class.java)
            return response.statusCode == 0
        } catch (e: Exception) {
            return false
        }
    }

    fun action(action: String): Boolean {
        try {
            val json = Http.postXWwwFormUrlEncoded(URLHolder.URL_ACTION, mapOf("action" to action))
                ?: throw MissingRequestException()
            val response = gson.fromJson(json, ActionResponse::class.java)

            if (response.statusCode == 1) throw MissingRequestException()

            return response.couldExecute
        } catch (e: Exception) {
            return false
        }
    }

    fun extendNormally(): Boolean {
        val json = Http.post(URLHolder.URL_EXTEND) ?: throw MissingRequestException()
        val response = gson.fromJson(json, ExtendResponse::class.java)

        return when (response.statusCode) {
            0 -> true
            1 -> false
            else -> throw CouldNotExtendException()
        }
    }

    fun logout(): Boolean {
        val response = Http.get(URLHolder.URL_LOGOUT) ?: throw MissingRequestException()

        return response.contains("MiRm | ログイン").also {
            if (it) {
                this.loggedIn = false
                this.serverId = ""
            }
        }
    }

    fun getTerms(): String? {
        return Http.get(URLHolder.URL_TERMS)
    }

    fun getLatestRSSFeeds(): MutableMap<String, String> {
        try {
            val data = Http.get(URLHolder.URL_RSS_FEEDS) ?: return mutableMapOf()
            val feeds = SyndFeedInput().build(InputSource(StringReader(data))).entries
            val result = mutableMapOf<String, String>()
            feeds.forEachIndexed { index, syndEntry ->
                if (index > 4) return@forEachIndexed
                result[syndEntry.title] = syndEntry.link
            }

            return result

        } catch (e: Exception) {
            e.printStackTrace()
            return mutableMapOf()
        }
    }

    private fun getCsrf(): String {
        val document = Jsoup.parse(Http.get(URLHolder.URL_LOGIN))
        return document.select("input[name=_csrf]").attr("value")
    }

}