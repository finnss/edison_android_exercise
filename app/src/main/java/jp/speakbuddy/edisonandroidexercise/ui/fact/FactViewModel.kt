package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.data.FactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// The View Model handling the UI State, particularly transitions between UI states.
@HiltViewModel
class FactViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var factRepository: FactRepository

    private val _uiState = MutableStateFlow(FactScreenUiState())
    val uiState: StateFlow<FactScreenUiState> = _uiState.asStateFlow()

    fun updateFact() {
        _uiState.update {
            FactScreenUiState(isLoading = true)
        }

        viewModelScope.launch {
            _uiState.update {
                try {
                    val fact = factRepository.getFact()
                    if (fact != null) {
                        FactScreenUiState(fact = fact, isLoading = false)
                    } else {
                        FactScreenUiState(
                            fact = Fact("something went wrong.", -1),
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    FactScreenUiState(
                        fact = Fact("something went wrong: ${e.message}", -1),
                        isLoading = false
                    )
                }
            }
        }
    }
}

/**
 * UiState for the facts list screen. Only contains the facts themselves and a loading flag.
 */
data class FactScreenUiState(
    val fact: Fact = Fact("", 0),
    val isLoading: Boolean = false
)