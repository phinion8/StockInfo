package com.priyanshu.stockinfo.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.priyanshu.stockinfo.domain.repositories.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class PreferenceManagerImpl(context: Context): PreferenceManager {

    private val dataStore = context.dataStore

    companion object {
        const val ON_BOARDING_PREFERENCES_KEY = "on_boarding_preferences"
        const val CACHED_TIME_KEY = "cached_time_key"
    }

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(ON_BOARDING_PREFERENCES_KEY)
        val cachedTimeKey = longPreferencesKey(CACHED_TIME_KEY)
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
            onBoardingState
        }
    }

    override suspend fun saveCachedTime(time: Long) {
        dataStore.edit { preferences->

            preferences[PreferencesKey.cachedTimeKey] = time

        }
    }

    override fun getCachedTime(): Flow<Long> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val cachedTimeState = preferences[PreferencesKey.cachedTimeKey] ?: 0
            cachedTimeState
        }
    }
}