package com.zhalz.voyageoflife.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zhalz.voyageoflife.utils.Const.DataStore.DATA_STORE_NAME
import com.zhalz.voyageoflife.utils.Const.DataStore.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreUser(context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)
    private val dataStore = context.datastore

    fun getUserCredentials(): Flow<String?> = dataStore.data.map {
        it[tokenKey]
    }

    suspend fun setUserCredentials(token: String) = dataStore.edit {
        it[tokenKey] = token
    }

    suspend fun clearUserCredentials() = dataStore.edit { it.clear() }

    companion object {
        private val tokenKey = stringPreferencesKey(TOKEN_KEY)
    }

}