package com.zhalz.voyageoflife.data.repository.auth

import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.zhalz.voyageoflife.data.local.datastore.DataStoreUser
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.data.remote.response.RegisterResponse
import com.zhalz.voyageoflife.utils.ApiResult
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService, private val dataStoreUser: DataStoreUser) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResult<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            val token = response.data?.token
            token?.let { dataStoreUser.setUserCredentials(it) }
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }
    }

    override suspend fun register(name: String, email: String, password: String): ApiResult<RegisterResponse> {
        return try {
            val response = apiService.register(name, email, password)
            ApiResult.Success(response)
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            ApiResult.Error(errorBody.message)
        }
    }

    override suspend fun isLogin(): Boolean {
        val token = dataStoreUser.getUserCredentials().first()
        return token != null
    }

    override suspend fun deleteUser(): Preferences = dataStoreUser.clearUserCredentials()

}