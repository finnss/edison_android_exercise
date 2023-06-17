package jp.speakbuddy.edisonandroidexercise.data

import android.content.Context
import jp.speakbuddy.edisonandroidexercise.network.FactDataSourceProvider
import javax.inject.Inject

interface FactRepository {
    suspend fun getFact(): Fact?
}

class FactRepositoryImpl @Inject constructor() : FactRepository {
    private lateinit var context: Context

    @Inject
    lateinit var factDataSourceProvider: FactDataSourceProvider

    override suspend fun getFact(): Fact? {
        return try {
            val apiResponse = factDataSourceProvider.fetchFact()
//            (context as MainActivity).dataStore.edit { settings ->
//                val currentCounterValue = settings["fact"] ?: 0
//                settings["fact"] = currentCounterValue + 1
//            }
            Fact(apiResponse.fact, apiResponse.length)
        } catch (e: Exception) {
            null
        }
    }
}