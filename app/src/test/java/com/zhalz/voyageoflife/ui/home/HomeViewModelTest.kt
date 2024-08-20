package com.zhalz.voyageoflife.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.zhalz.voyageoflife.utils.DataDummy
import com.zhalz.voyageoflife.utils.MainDispatcherRule
import com.zhalz.voyageoflife.StoryPagingSource
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import com.zhalz.voyageoflife.data.repository.story.StoryRepository
import com.zhalz.voyageoflife.ui.adapter.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var authRepository: AuthRepository

    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val dummyUsers = DataDummy.generateDummyStory()
        val data: PagingData<StoryData> = StoryPagingSource.snapshot(dummyUsers)
        val expectedUsers = MutableStateFlow<PagingData<StoryData>>(PagingData.empty())
        expectedUsers.emit(data)

        `when`(storyRepository.getPagingStories()).thenReturn(expectedUsers)

        val storyViewModel = HomeViewModel(storyRepository, authRepository)
        val actualStories = storyViewModel.getPagingStories().first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)

        assertNotNull(differ.snapshot())
        assertEquals(dummyUsers.size, differ.snapshot().size)
        assertEquals(dummyUsers[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Stories Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryData> = PagingData.from(emptyList())
        val expectedUsers = MutableStateFlow<PagingData<StoryData>>(PagingData.empty())
        expectedUsers.emit(data)

        `when`(storyRepository.getPagingStories()).thenReturn(expectedUsers)

        val storyViewModel = HomeViewModel(storyRepository, authRepository)
        val actualStories: PagingData<StoryData> = storyViewModel.getPagingStories().first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)

        assertEquals(0, differ.snapshot().size)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

}