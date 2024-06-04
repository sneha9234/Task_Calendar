package com.example.taskcalendar.common

import android.content.Context
import androidx.datastore.core.DataStore
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


@Singleton
class PrefsDataStore @Inject constructor(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    private val dataStore = context.dataStore

    val userId: Flow<Int?> = dataStore.data
        .catch { exception ->
            // Handle IO errors.
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Retrieve the userId from preferences, or return null if it doesn't exist.
            preferences[PreferencesKeys.USER_ID]
        }

    suspend fun storeUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    private object PreferencesKeys {
        val USER_ID = intPreferencesKey("user_id")
    }
}
