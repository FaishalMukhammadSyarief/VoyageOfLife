package com.zhalz.voyageoflife.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhalz.voyageoflife.data.remote.response.StoryData

class StoryPagingSource: PagingSource<Int, LiveData<List<StoryData>>>() {
    companion object {
        fun snapshot(items: List<StoryData>): PagingData<StoryData> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryData>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryData>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}