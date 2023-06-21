package com.hcapps.xpenzave.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.asLiveData
import com.hcapps.xpenzave.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreServiceImpl @Inject constructor(
    private val userPreferencesDataStore: DataStore<Preferences>
): DataStoreService {

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_CURRENCY_CODE = stringPreferencesKey("currency_code")
    }

    override suspend fun saveUser(user: User) {
        userPreferencesDataStore.edit { preferences ->
            preferences[USER_ID] = user.userId
            preferences[USER_EMAIL] = user.email ?: ""
            preferences[USER_CURRENCY_CODE] = user.currencyCode ?: ""
        }
    }

    override fun getUserFlow(): Flow<User> {
        return userPreferencesDataStore.data
            .map { preferences ->
                User(
                    userId = preferences[USER_ID] ?: "",
                    email = preferences[USER_EMAIL],
                    currencyCode = preferences[USER_CURRENCY_CODE]
                )
            }
    }

    override suspend fun clear() {
        userPreferencesDataStore.edit {  }
    }

    override fun getUser(): User = getUserFlow().asLiveData().value ?: User()
}