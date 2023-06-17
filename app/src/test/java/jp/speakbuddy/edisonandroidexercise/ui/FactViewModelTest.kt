package jp.speakbuddy.edisonandroidexercise.ui

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.data.FactRepository
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FactViewModelTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @MockK
    lateinit var factRepository: FactRepository

    @InjectMockKs
    var viewModel = FactViewModel()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        hiltAndroidRule.inject()
    }

    @Test
    fun testFactViewModelGetFact() = runTest {
        val fact = Fact("Test fact", 10)

        launch {
            coEvery { factRepository.getFact() } returns fact

            val initialUiState = viewModel.uiState.value.copy()

            assert(!initialUiState.isLoading)
            assertEquals(initialUiState.fact.text, "")
            assertEquals(initialUiState.fact.length, 0)

            viewModel.updateFact()

            val newUiState = viewModel.uiState.value.copy()

            assert(!initialUiState.isLoading)
            assertEquals(newUiState.fact.text, fact.text)
            assertEquals(newUiState.fact.length, fact.length)

            coVerify { factRepository.getFact() }
        }
    }
}
