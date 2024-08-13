package com.zhalz.voyageoflife.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResponse = MutableSharedFlow<ApiResult<LoginResponse>>()
    val loginResponse = _loginResponse.asSharedFlow()

    fun login() = viewModelScope.launch {
        _loginResponse.emit(ApiResult.Loading())
        val result = authRepository.login(email.value.orEmpty(), password.value.orEmpty())
        _loginResponse.emit(result)
    }

}