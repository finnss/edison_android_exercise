package jp.speakbuddy.edisonandroidexercise.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jp.speakbuddy.edisonandroidexercise.data.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Store the latest fetched Fact. Currently unused for anything else that a demo implementation of
// Jetpack Datastore.
class FactsStore @Inject constructor(
    private val context: Context,
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("facts")
        private val FACTS_KEY = stringPreferencesKey("facts")
    }

    val getLocalFacts: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[FACTS_KEY] ?: ""
    }

    suspend fun saveFact(fact: Fact) {
        context.dataStore.edit { preferences ->
            preferences[FACTS_KEY] = fact.text
        }
    }
}
