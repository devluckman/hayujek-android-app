package com.man.hayujek.data.preferences

import android.content.Context
import org.koin.core.annotation.Single

/**
 *
 * Created by Lukmanul Hakim on  15/07/22
 * devs.lukman@gmail.com
 */
@Single
class CacheStorage(val context: Context) {

    fun clearToken() {
        deletePersistData(PREF_API_TOKEN)
    }

    fun getApiToken(): String = getPersistedData(PREF_API_TOKEN) ?: ""

    fun setApiToken(token: String?) {
        setPersistData(PREF_API_TOKEN, token)
    }

    private fun getPersistedData(key: String): String? {
        val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preference?.getString(key, null)
    }

    private fun setPersistData(key: String, value: String?) {
        val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preference?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    private fun deletePersistData(key: String) {
        val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preference?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    companion object {
        const val PREF_NAME = "Cache.HayuJek"
        const val PREF_API_TOKEN = "KEY_PREF_API_TOKEN"
    }

}