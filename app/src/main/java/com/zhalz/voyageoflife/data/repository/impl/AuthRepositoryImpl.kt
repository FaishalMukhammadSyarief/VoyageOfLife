package com.zhalz.voyageoflife.data.repository.impl

import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.data.repository.AuthRepository
import com.zhalz.voyageoflife.utils.ApiResult
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResult<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }
    }

}