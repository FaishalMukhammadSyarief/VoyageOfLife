package com.zhalz.voyageoflife.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zhalz.voyageoflife.data.remote.ApiService
import com.zhalz.voyageoflife.data.remote.response.ErrorResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    private val _storiesResponse = MutableSharedFlow<ApiResult<StoriesResponse>>()
    val storiesResponse = _storiesResponse.asSharedFlow()

    fun getStories() = viewModelScope.launch {
        try {
            _storiesResponse.emit(ApiResult.Loading())
            val response = apiService.getStories()
            _storiesResponse.emit(ApiResult.Success(response))
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            _storiesResponse.emit(ApiResult.Error(errorBody.message))
        }
    }

}