package com.zhalz.voyageoflife.data.repository.auth

import androidx.datastore.preferences.core.Preferences
import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.data.remote.response.RegisterResponse
import com.zhalz.voyageoflife.utils.ApiResult

interface AuthRepository {

    suspend fun login(email: String, password: String) : ApiResult<LoginResponse>
    suspend fun register(name: String, email: String, password: String) : ApiResult<RegisterResponse>
    suspend fun isLogin() : Boolean
    suspend fun deleteUser() : Preferences

}