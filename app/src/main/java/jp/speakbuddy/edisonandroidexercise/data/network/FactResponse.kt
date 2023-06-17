package jp.speakbuddy.edisonandroidexercise.data.network

import kotlinx.serialization.Serializable

/**
 * Internal model used to represent a task obtained from the network. This is used inside the data
 * layer only.
 */
@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)
