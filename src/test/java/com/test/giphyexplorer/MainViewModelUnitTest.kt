package com.test.giphyexplorer

import androidx.paging.PagingData
import app.cash.turbine.test
import com.test.giphyexplorer.data.GifItem
import com.test.giphyexplorer.data.GiphyRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelUnitTest {

    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var giphyRepository: GiphyRepository

    private lateinit var subject: MainViewModel
    private lateinit var mocks: AutoCloseable

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mocks = openMocks(this)
        subject = MainViewModel(giphyRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mocks.close()
    }

    @Test
    fun `EmptyInput State is emitted when launched`() = runTest {
        subject.uiState.test {
            assertEquals(awaitItem(), PageUiState.EmptyInput)
        }
    }

    @Test
    fun `Loaded State is emitted when search executed`() = runTest {
        val pagingData = flowOf<PagingData<GifItem>>(PagingData.empty())
        val testSearchQuery = "testing"
        whenever(giphyRepository.getSearchResults(testSearchQuery)).thenReturn(pagingData)

        subject.search(testSearchQuery)
        subject.uiState.test {
            this@runTest.testScheduler.apply { advanceTimeBy(800); runCurrent() }
            assertEquals(expectMostRecentItem(), PageUiState.Loaded(pagingData))
            cancelAndIgnoreRemainingEvents()
        }
    }
}