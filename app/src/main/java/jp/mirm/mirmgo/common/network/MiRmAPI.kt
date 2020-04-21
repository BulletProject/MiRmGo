package jp.mirm.mirmgo.common.network

import com.google.gson.Gson
import com.rometools.rome.io.SyndFeedInput
import jp.mirm.mirmgo.common.exception.CouldNotExtendException
import jp.mirm.mirmgo.common.exception.MissingRequestException
import jp.mirm.mirmgo.common.network.model.*
import org.jsoup.Jsoup
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.Exception
import java.net.URLEncoder

object MiRmAPI {

    private val gson = Gson()
    var loggedIn = false
    var serverId = ""

    fun login(serverId: String, password: String): LoginResponse {
        try {
            val response = Http.postXWwwFormUrlEncoded(
                URLHolder.URL_AUTHENTICATE, mapOf(
                    "serverId" to URLEncoder.encode(serverId),
                    "password" to URLEncoder.encode(password),
                    "_csrf" to getCsrf()
                )
            ) ?: return LoginResponse(AbstractResponse.STATUS_ERROR, LoginResponse.LOGIN_STATUS_FAILED)

            if (response.contains("期限切れでサーバーが削除されました。")) return LoginResponse(AbstractResponse.STATUS_SUCCEEDED, LoginResponse.LOGIN_STATUS_DELETED_SERVER)
            if (response.contains("サーバー削除ボタンによって削除されています。")) return LoginResponse(AbstractResponse.STATUS_SUCCEEDED, LoginResponse.LOGIN_STATUS_USER_DELETED)

            return response.contains("MiRm | コントロールパネル").let {
                if (it) {
                    this.loggedIn = true
                    this.serverId = serverId
                    return LoginResponse(AbstractResponse.STATUS_SUCCEEDED, LoginResponse.LOGIN_STATUS_SUCCEEDED)

                } else {
                    return LoginResponse(AbstractResponse.STATUS_SUCCEEDED, LoginResponse.LOGIN_STATUS_FAILED)
                }
            }

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return LoginResponse(AbstractResponse.STATUS_OUT_OF_SERVICE, LoginResponse.LOGIN_STATUS_FAILED)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return LoginResponse(AbstractResponse.STATUS_ERROR, LoginResponse.LOGIN_STATUS_FAILED)
    }

    fun getServerData(): ServerDataResponse {
        try {
            val json = Http.get(URLHolder.URL_SERVER_DATA) ?: throw MissingRequestException(MissingRequestException.CODE_UNKNOWN)
            return gson.fromJson(json, ServerDataResponse::class.java).also { it.apiStatusCode = AbstractResponse.STATUS_SUCCEEDED }

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return ServerDataResponse("", "", 0, 0, 0, "", false, AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ServerDataResponse("", "", 0, 0, 0, "", false, AbstractResponse.STATUS_ERROR)
    }

    fun createBDSServer(serverId: String, passwordEncrypted: String, gameMode: String, difficulty: String, permission: String): CreateBDSServerResponse {
        try {
            val json = Http.post(URLHolder.URL_CREATE_SERVER, mapOf(
                "serverId" to serverId,
                "passwordEncrypted" to passwordEncrypted,
                "gameMode" to gameMode,
                "difficulty" to difficulty,
                "permission" to permission
            ))
            return gson.fromJson(json, CreateBDSServerResponse::class.java).also { it.apiStatusCode = AbstractResponse.STATUS_SUCCEEDED }

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return CreateBDSServerResponse("", false, "", 0, AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CreateBDSServerResponse("", false, "", 0, AbstractResponse.STATUS_ERROR)
    }

    fun sendCommand(command: String): CommandResponse {
        try {
            val json =
                Http.postXWwwFormUrlEncoded(URLHolder.URL_SEND_COMMAND, mapOf("command" to command))
                    ?: return CommandResponse("", "", 0, AbstractResponse.STATUS_ERROR)
            return gson.fromJson(json, CommandResponse::class.java).also { it.apiStatusCode = AbstractResponse.STATUS_SUCCEEDED }

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return CommandResponse("", "", 0, AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CommandResponse("", "", 0, AbstractResponse.STATUS_ERROR)
    }

    fun action(action: String): ActionResponse {
        try {
            val json = Http.postXWwwFormUrlEncoded(URLHolder.URL_ACTION, mapOf("action" to action))
                ?: return ActionResponse("", "", false, 0, AbstractResponse.STATUS_ERROR)
            return gson.fromJson(json, ActionResponse::class.java).also { it.apiStatusCode = AbstractResponse.STATUS_SUCCEEDED }

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return ActionResponse("", "", false, 0, AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ActionResponse("", "", false, 0, AbstractResponse.STATUS_ERROR)
    }

    fun extendNormally(): Boolean {
        val json = Http.post(URLHolder.URL_EXTEND) ?: throw MissingRequestException(MissingRequestException.CODE_UNKNOWN)
        val response = gson.fromJson(json, ExtendResponse::class.java)

        return when (response.statusCode) {
            0 -> true
            1 -> false
            else -> throw CouldNotExtendException()
        }
    }

    fun logout(): LogoutResponse {
        try {
            val response = Http.get(URLHolder.URL_LOGOUT) ?: return LogoutResponse(
                false,
                AbstractResponse.STATUS_ERROR
            )

            val loggedOut = response.contains("MiRm | ログイン").also {
                if (it) {
                    this.loggedIn = false
                    this.serverId = ""
                }
            }

            return LogoutResponse(loggedOut, AbstractResponse.STATUS_SUCCEEDED)

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return LogoutResponse(false, AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return LogoutResponse(false, AbstractResponse.STATUS_ERROR)
    }

    fun getTerms(): String? {
        return Http.get(URLHolder.URL_TERMS)
    }

    fun getLatestRSSFeeds(): RSSResponse {
        try {
            val data = Http.get(URLHolder.URL_RSS_FEEDS) ?: return RSSResponse(mutableMapOf(), AbstractResponse.STATUS_ERROR)
            val feeds = SyndFeedInput().build(InputSource(StringReader(data))).entries
            val result = mutableMapOf<String, String>()
            feeds.forEachIndexed { index, syndEntry ->
                if (index > 4) return@forEachIndexed
                result[syndEntry.title] = syndEntry.link
            }

            return RSSResponse(result, AbstractResponse.STATUS_SUCCEEDED)

        } catch (e: MissingRequestException) {
            if (e.errorCode == 503) {
                return RSSResponse(mutableMapOf(), AbstractResponse.STATUS_OUT_OF_SERVICE)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return RSSResponse(mutableMapOf(), AbstractResponse.STATUS_ERROR)
    }

    private fun getCsrf(): String {
        val document = Jsoup.parse(Http.get(URLHolder.URL_LOGIN))
        return document.select("input[name=_csrf]").attr("value")
    }

}