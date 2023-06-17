package jp.speakbuddy.edisonandroidexercise.data

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import jp.speakbuddy.edisonandroidexercise.data.local.FactsStore
import jp.speakbuddy.edisonandroidexercise.data.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.network.FactsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FactRepositoryTest {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @MockK
    lateinit var networkDataSource: FactsNetworkDataSource

    @MockK
    lateinit var localDataSource: FactsStore

    lateinit var dispatcher: TestDispatcher

    private lateinit var factRepository: FactRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        hiltAndroidRule.inject() // 6

        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)

        factRepository = FactRepositoryImpl(networkDataSource, localDataSource, dispatcher)
    }

    @Test
    fun testFactRepositoryGetFuck() = runTest(dispatcher) {
        factRepository = FactRepositoryImpl(networkDataSource, localDataSource, dispatcher)

        val fact = Fact("Test fact", 10)
        coEvery { networkDataSource.fetchFact() } returns FactResponse("Test fact", 10)
        coEvery { localDataSource.saveFact(any()) } returns Unit

        launch {
            // Act
            val result = factRepository.getFact()

            // Assert
            Assertions.assertEquals(fact, result)
            coVerify { networkDataSource.fetchFact() }
            coVerify { localDataSource.saveFact(fact) }
        }
    }
}
