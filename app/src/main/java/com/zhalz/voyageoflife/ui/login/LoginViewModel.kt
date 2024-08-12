package com.zhalz.voyageoflife.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResponse = MutableSharedFlow<ApiResult<LoginResponse>>()
    val loginResponse = _loginResponse.asSharedFlow()

    fun login() = viewModelScope.launch {
        try {
            _loginResponse.emit(ApiResult.Loading())
            val response = apiService.login(email.value.orEmpty(), password.value.orEmpty())
            _loginResponse.emit(ApiResult.Success(response))
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            _loginResponse.emit(ApiResult.Error(errorBody.message))
        }
    }

}