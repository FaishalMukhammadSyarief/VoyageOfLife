package com.zhalz.voyageoflife.data.repository.story

import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import java.io.File

interface StoryRepository {

    suspend fun getStories() : ApiResult<StoriesResponse>
    suspend fun uploadStories(description: String, image: File?) : ApiResult<UploadResponse>

}