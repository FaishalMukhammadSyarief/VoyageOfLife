package com.zhalz.voyageoflife.data.repository.stories

import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.utils.ApiResult
import retrofit2.HttpException
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(private val apiService: ApiService) : StoriesRepository {

    override suspend fun getStories(): ApiResult<StoriesResponse> {
        return try {
            val response = apiService.getStories()
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }
    }

}