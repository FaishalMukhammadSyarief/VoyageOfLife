package com.zhalz.voyageoflife.data.repository.story

import androidx.paging.PagingData
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {

    suspend fun getPagingStories() : Flow<PagingData<StoryData>>
    suspend fun getStoriesWithLocation(): ApiResult<StoriesResponse>
    suspend fun uploadStories(description: String, image: File) : ApiResult<UploadResponse>

}