package com.zhalz.voyageoflife.data.repository.story

import android.location.Location
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
    suspend fun uploadStories(image: File, description: String, location: Location?) : ApiResult<UploadResponse>

}