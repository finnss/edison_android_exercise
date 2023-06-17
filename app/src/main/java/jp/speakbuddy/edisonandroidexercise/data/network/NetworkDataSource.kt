package jp.speakbuddy.edisonandroidexercise.data.network

/**
 * Main entry point for accessing tasks data from the network.
 *
 */
interface NetworkDataSource {
    suspend fun fetchFact(): FactResponse
}
