package com.zhalz.voyageoflife.data.repository

import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.utils.ApiResult

interface AuthRepository {

    suspend fun login(email: String, password: String) : ApiResult<LoginResponse>

}