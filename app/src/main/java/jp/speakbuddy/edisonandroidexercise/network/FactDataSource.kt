package jp.speakbuddy.edisonandroidexercise.network

import android.content.Context
import jp.speakbuddy.edisonandroidexercise.IoDispatcher
import jp.speakbuddy.edisonandroidexercise.MainActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import javax.inject.Inject

interface FactDataSource {
    @GET("fact")
    suspend fun fetchFact(): FactResponse
}

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)

class FactDataSourceProvider @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FactDataSource {
    private lateinit var context: Context

//    @Inject
//    lateinit var factsApi: FactsApi

    // Move the execution to an IO-optimized thread
    override suspend fun fetchFact(): FactResponse =
        withContext(ioDispatcher) {
            (context as MainActivity).factsApi.provide().fetchFact()
        }
}
