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

    fun getPassword(): String? {
        return PasswordManager.decrypt(getString("password") ?: return null)
    }

    fun getServerId(): String? {
        return PasswordManager.decrypt(getString("server_id") ?: return null)
    }

    private fun putString(key: String, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String, default: String? = null): String? {
        return preferences.getString(key, default)
    }

}