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
//            val existing = mutableListOf<>(preferences[FACTS_KEY])
//            existing.add(fact.text)
            preferences[FACTS_KEY] = fact.text
        }
    }
}