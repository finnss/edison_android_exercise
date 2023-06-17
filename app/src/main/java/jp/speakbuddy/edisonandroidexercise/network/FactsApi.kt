package jp.speakbuddy.edisonandroidexercise.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Makes facts-related network synchronous requests.
interface FactsApi {
    fun fetchFact(): FactResponse
}

object FactsApiImpl {
    fun provide(): FactsApi =
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(FactsApi::class.java)
}
