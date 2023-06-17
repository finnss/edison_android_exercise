package jp.speakbuddy.edisonandroidexercise.data.network

import jp.speakbuddy.edisonandroidexercise.network.FactsApiImpl
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FactsNetworkDataSource @Inject constructor() : NetworkDataSource {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    override suspend fun fetchFact(): FactResponse = accessMutex.withLock {
        FactsApiImpl.provide().fetchFact()
    }
}
