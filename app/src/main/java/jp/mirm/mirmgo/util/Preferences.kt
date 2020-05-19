package jp.mirm.mirmgo.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.mirm.mirmgo.MyApplication

object Preferences {

    private val preferences =
        MyApplication.getApplication().getSharedPreferences("DATA", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun addServer(serverId: String, rawPassword: String) {
        val servers = getServersMap()
        servers[serverId] = PasswordManager.encrypt(rawPassword)
        putString("servers", gson.toJson(servers))
    }

    fun removeServer(serverId: String) {
        val servers = getServersMap()
        servers.remove(serverId)
        putString("servers", gson.toJson(servers))
    }

    fun getDecryptedPassword(serverId: String): String? {
        return PasswordManager.decrypt(getServersMap().get(serverId) ?: return null)
    }

    fun setCurrentServer(serverId: String) {
        putString("current_server", serverId)
    }

    fun removeCurrentServer() {
        putString("current_server", null)
    }

    fun getCurrentServerId(): String? {
        return getString("current_server")
    }

    fun isOtherServersAllowed(): Boolean {
        return getBoolean("allow_other_servers")
    }

    private fun putString(key: String, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String, default: String? = null): String? {
        return preferences.getString(key, default)
    }

    private fun putBoolean(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getServersMap(): MutableMap<String, String> {
        val servers = gson.fromJson<MutableMap<String, String>>(
            getString(
                "servers", gson.toJson(mutableMapOf<String, String>())
            ), object : TypeToken<MutableMap<String, String>>() {}.type
        )
        return servers
    }

}