package com.zhalz.voyageoflife.data.repository.story

import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val apiService: ApiService) : StoryRepository {

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

    override suspend fun uploadStories(description: String, image: File?): ApiResult<UploadResponse> {

        val fileBody = image?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = fileBody?.let {
            MultipartBody.Part.createFormData("profile", image.name, it)
        }

        return try {
            val response = apiService.uploadStory(description = description, file = filePart)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }

    }

}