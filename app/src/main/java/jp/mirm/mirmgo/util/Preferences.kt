package jp.mirm.mirmgo.util

import android.content.Context
import jp.mirm.mirmgo.MyApplication

object Preferences {

    private val preferences = MyApplication.getApplication().getSharedPreferences("DATA", Context.MODE_PRIVATE)

    fun setPassword(password: String?) {
        putString("password", if (password != null) PasswordManager.encrypt(password) else null)
    }

    fun setServerId(serverId: String?) {
        putString("server_id", if (serverId != null) PasswordManager.encrypt(serverId) else null)
    }

    fun setOtherServersAllowed(isAllowed: Boolean) {
        putBoolean("allow_other_servers", isAllowed)
    }

    fun getPassword(): String? {
        return PasswordManager.decrypt(getString("password") ?: return null)
    }

    fun getServerId(): String? {
        return PasswordManager.decrypt(getString("server_id") ?: return null)
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

}