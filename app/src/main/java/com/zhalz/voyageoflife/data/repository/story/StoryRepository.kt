package com.zhalz.voyageoflife.data.repository.story

import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.utils.ApiResult

interface StoryRepository {

    suspend fun getStories() : ApiResult<StoriesResponse>

}