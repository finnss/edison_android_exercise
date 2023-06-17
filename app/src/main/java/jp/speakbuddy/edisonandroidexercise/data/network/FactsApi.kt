package jp.speakbuddy.edisonandroidexercise.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import jp.speakbuddy.edisonandroidexercise.data.network.FactResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

// Makes facts-related network synchronous requests.
interface FactsApi {
    @GET("fact")
    suspend fun fetchFact(): FactResponse
}

object FactsApiImpl {
    fun provide(): FactsApi =
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(FactsApi::class.java)
}
