package com.zhalz.voyageoflife.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import com.zhalz.voyageoflife.data.repository.story.StoryRepository
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val storyRepository: StoryRepository, private val authRepository: AuthRepository): ViewModel() {

    private val _storiesResponse = MutableSharedFlow<ApiResult<StoriesResponse>>()
    val storiesResponse = _storiesResponse.asSharedFlow()

    fun getStories() = viewModelScope.launch {
        _storiesResponse.emit(ApiResult.Loading())
        val result = storyRepository.getStories()
        _storiesResponse.emit(result)
    }

    suspend fun clearUserCredential() = authRepository.deleteUser()

}