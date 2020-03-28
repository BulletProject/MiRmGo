package jp.mirm.mirmgo.common.network

import com.google.gson.Gson
import jp.mirm.mirmgo.common.exception.CouldNotExtendException
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.common.network.model.CommandResponse
import jp.mirm.mirmgo.common.network.model.ExtendResponse
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import org.jsoup.Jsoup
import java.net.URLEncoder

object MiRmAPI {

    private val gson = Gson()
    var loggedIn = false

    fun login(serverId: String, password: String): Boolean {
        val response =  Http.postXWwwFormUrlEncoded(URLHolder.URL_AUTHENTICATE, mapOf(
            "serverId" to URLEncoder.encode(serverId),
            "password" to URLEncoder.encode(password),
            "_csrf" to getCsrf()
         )) ?: throw MissingRequestException()

        return response.contains("MiRm | コントロールパネル").also {
            if (it) this.loggedIn = true
        }
    }

    fun getServerData(): ServerDataResponse {
        val json = Http.get(URLHolder.URL_SERVER_DATA) ?: throw MissingRequestException()
        return gson.fromJson(json, ServerDataResponse::class.java)
    }

    fun sendCommand(command: String): Boolean {
        val json = Http.postXWwwFormUrlEncoded(URLHolder.URL_SEND_COMMAND, mapOf("command" to command)) ?: throw MissingRequestException()
        val response = gson.fromJson(json, CommandResponse::class.java)
        return response.statusCode == 0
    }

    fun action(action: String): Boolean {
        val json = Http.postXWwwFormUrlEncoded(URLHolder.URL_ACTION, mapOf("action" to action)) ?: throw MissingRequestException()
        val response = gson.fromJson(json, ActionResponse::class.java)

        if (response.statusCode == 1) throw MissingRequestException()

        return response.couldExecute
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
            if (!it) this.loggedIn = false
        }
    }

    private fun getCsrf(): String {
        val document = Jsoup.parse(Http.get(URLHolder.URL_LOGIN))
        return document.select("input[name=_csrf]").attr("value")
    }

}