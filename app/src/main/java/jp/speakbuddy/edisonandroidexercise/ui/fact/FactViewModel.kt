package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.data.FactRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var factRepository: FactRepository

    fun updateFact(completion: () -> Unit): String =
        runBlocking {
            try {
                val fact = factRepository.getFact()!!.fact
                fact
            } catch (e: Throwable) {
                "something went wrong. error = ${e.message}"
            }.also { completion() }
        }
}
