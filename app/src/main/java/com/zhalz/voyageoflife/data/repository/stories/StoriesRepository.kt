package com.zhalz.voyageoflife.data.repository.stories

import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.utils.ApiResult

interface StoriesRepository {

    suspend fun getStories() : ApiResult<StoriesResponse>

}