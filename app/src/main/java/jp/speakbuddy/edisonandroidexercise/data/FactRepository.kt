package jp.speakbuddy.edisonandroidexercise.data

import jp.speakbuddy.edisonandroidexercise.IoDispatcher
import jp.speakbuddy.edisonandroidexercise.data.local.FactsStore
import jp.speakbuddy.edisonandroidexercise.data.network.FactsNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FactRepository {
    suspend fun getFact(): Fact?
}

class FactRepositoryImpl @Inject constructor(
    private val networkDataSource: FactsNetworkDataSource,
    private val localDataSource: FactsStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : FactRepository {
    override suspend fun getFact(): Fact {
        return withContext(dispatcher) {
            val apiResponse = networkDataSource.fetchFact()
            val fact = Fact(apiResponse.fact, apiResponse.length)
            localDataSource.saveFact(fact)
            fact
        }
    }
}