package com.example.app_recetas.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val LOGIN_STATE = booleanPreferencesKey("login_state")
    }

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGIN_STATE] ?: false
        }
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE] = isLoggedIn
        }
    }

    suspend fun clearLoginState() {
        dataStore.edit { preferences ->
            preferences.remove(LOGIN_STATE)
        }
    }


}
