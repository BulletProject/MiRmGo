package jp.mirm.mirmgo.common.network

import com.google.gson.Gson
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.common.network.model.CommandResponse
import jp.mirm.mirmgo.common.network.model.ServerDataResponse
import org.jsoup.Jsoup
import java.net.URLEncoder

object MiRmAPI {

    private val gson = Gson()
    private var serverId: String? = null
    var loggedIn = false

    fun login(serverId: String, password: String): Boolean {
        val response =  Http.post_X_WWW_FORM_URL_ENCODED(URLHolder.URL_AUTHENTICATE, mapOf(
            "serverId" to URLEncoder.encode(serverId),
            "password" to URLEncoder.encode(password),
            "_csrf" to getCsrf()
         ))

        val document = Jsoup.parse(response)
        val title = document.title()

        return (title == "MiRm | コントロールパネル").also {
            if (it) {
                this.serverId = serverId
                this.loggedIn = true
            }
        }
    }

    fun getServerData(): ServerDataResponse {
        val json = Http.get(URLHolder.URL_SERVER_DATA) ?: throw MissingRequestException()
        return gson.fromJson(json, ServerDataResponse::class.java)
    }

    fun sendCommand(command: String): Boolean {
        val json = Http.post_X_WWW_FORM_URL_ENCODED(URLHolder.URL_SEND_COMMAND, mapOf("command" to command)) ?: throw MissingRequestException()
        val response = gson.fromJson(json, CommandResponse::class.java)
        return response.statusCode == 0
    }

    fun action(action: String): Boolean {
        val json = Http.post_X_WWW_FORM_URL_ENCODED(URLHolder.URL_ACTION, mapOf("action" to action)) ?: throw MissingRequestException()
        val response = gson.fromJson(json, ActionResponse::class.java)

        if (response.statusCode == 1) throw MissingRequestException()

        return response.couldExecute
    }

    private fun getCsrf(): String {
        val document = Jsoup.parse(Http.get(URLHolder.URL_LOGIN))
        return document.select("input[name=_csrf]").attr("value")
    }

}