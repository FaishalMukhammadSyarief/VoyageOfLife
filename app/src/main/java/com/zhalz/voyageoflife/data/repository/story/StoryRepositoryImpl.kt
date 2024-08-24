package com.zhalz.voyageoflife.data.repository.story

import android.location.Location
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.zhalz.voyageoflife.data.local.room.AppDatabase
import com.zhalz.voyageoflife.data.paging.StoryRemoteMediator
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.DataMapper.toStoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val apiService: ApiService, private val database: AppDatabase) : StoryRepository {

    override suspend fun getPagingStories(): Flow<PagingData<StoryData>> =
        @OptIn(ExperimentalPagingApi::class)
        Pager(
            config = PagingConfig(pageSize = 4, initialLoadSize = 8),
            remoteMediator = StoryRemoteMediator(apiService, database),
            pagingSourceFactory = { database.storyDao().getStories() }
        ).flow.map(::toStoryData)

    override suspend fun getStoriesWithLocation(): ApiResult<StoriesResponse> {
        return try {
            val response = apiService.getStories(location = 1)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }
    }

    override suspend fun uploadStories(image: File, description: String, location: Location?): ApiResult<UploadResponse> {

        val descriptionBody = description.toRequestBody("text/plain".toMediaType())

        val fileBody = image.asRequestBody("image/jpg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("photo", image.name, fileBody)

        var latBody: RequestBody? = null
        var lonBody: RequestBody? = null

        location?.let {
            latBody = location.latitude.toString().toRequestBody("text/plain".toMediaType())
            lonBody = location.longitude.toString().toRequestBody("text/plain".toMediaType())
        }

        return try {
            val response = apiService.uploadStory(filePart, descriptionBody, latBody, lonBody)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }

    }

}