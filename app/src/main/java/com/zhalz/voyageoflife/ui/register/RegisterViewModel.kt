package com.zhalz.voyageoflife.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.voyageoflife.data.remote.response.RegisterResponse
import com.zhalz.voyageoflife.data.repository.AuthRepository
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _registerResponse = MutableSharedFlow<ApiResult<RegisterResponse>>()
    val registerResponse = _registerResponse.asSharedFlow()

    fun register() = viewModelScope.launch {
        _registerResponse.emit(ApiResult.Loading())
        val result = authRepository.register(name.value.orEmpty(), email.value.orEmpty(), password.value.orEmpty())
        _registerResponse.emit(result)
    }

}