package com.zhalz.voyageoflife.data.repository.story

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {

    fun getPagingStories() : LiveData<PagingData<StoryData>>
    suspend fun getStoriesWithLocation(page: Int? = null, size: Int? = null, location: Int = 0): ApiResult<StoriesResponse>
    suspend fun uploadStories(description: String, image: File) : ApiResult<UploadResponse>

}