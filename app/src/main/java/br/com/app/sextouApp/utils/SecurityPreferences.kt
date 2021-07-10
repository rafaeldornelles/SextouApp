package br.com.app.sextouApp.utils

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {

    private val mPreferences: SharedPreferences =
        context.getSharedPreferences(SECURITY_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun store(key: String, value: String) {
        mPreferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }

    fun get(key: String): String? {
        return mPreferences.getString(key, null)
    }
}