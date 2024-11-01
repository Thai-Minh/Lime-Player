package com.example.limeplayer.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.limeplayer.BaseApplication.Companion.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "allDownloader_pref")
private val dataStore: DataStore<Preferences>
    get() = application.dataStore

private val storeScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

private fun dataStoreWrite(block: suspend () -> Unit) {
    storeScope.launch {
        try {
            block()
        } catch (_: Exception) {
        }
    }
}

private val data = dataStore.data.catch { exception ->
    if (exception is IOException) {
        emit(emptyPreferences())
    } else {
        throw exception
    }
}

val LANGUAGE_CODE_KEY = stringPreferencesKey("LANGUAGE_CODE_KEY")
val LanguageCode: Flow<String> = data.map { preferences ->
    preferences[LANGUAGE_CODE_KEY] ?: "en"
}.distinctUntilChanged()

fun updateLanguageCode(value: String) = dataStoreWrite {
    dataStore.edit { settings ->
        settings[LANGUAGE_CODE_KEY] = value
    }
}