package com.man.hayujek.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
@Single
class DataStoreManager(val context: Context) {

    // region Token

    suspend fun setToken(tokenValue: String){
        context.counterDataStore.edit { preferences ->
            preferences[TOKEN_KEY] = tokenValue
        }
    }

    val token : Flow<String>
        get() = context.counterDataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }

    // endregion

    companion object {
        private const val DATASTORE_NAME = "hayujek_preferences"

        private val TOKEN_KEY = stringPreferencesKey("token_key");

        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}