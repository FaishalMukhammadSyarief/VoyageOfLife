package com.zhalz.voyageoflife.data.repository.story

import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.utils.ApiResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    override suspend fun uploadStories(description: String, image: File): ApiResult<UploadResponse> {

        val descriptionBody = description.toRequestBody("text/plain".toMediaType())
        val fileBody = image.asRequestBody("image/jpg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("photo", image.name, fileBody)

        return try {
            val response = apiService.uploadStory(description = descriptionBody, photo = filePart)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }

    }

}