package com.hcapps.xpenzave.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    companion object {
        const val SETTINGS_IS_LOGGED_IN_KEY = "is_logged_in"
    }

    private val Context.datastore by preferencesDataStore(name = "settings")

    suspend fun saveBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        context.datastore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[dataStoreKey] ?: false
    }

}