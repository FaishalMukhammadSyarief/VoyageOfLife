package com.zhalz.voyageoflife.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.data.repository.story.StoryRepository
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    private val _uploadResponse = MutableSharedFlow<ApiResult<UploadResponse>>()
    val uploadResponse = _uploadResponse.asSharedFlow()

    fun uploadStory(description: String, image: File?) = viewModelScope.launch {
        _uploadResponse.emit(ApiResult.Loading())
        val result = storyRepository.uploadStories(description, image)
        _uploadResponse.emit(result)
    }

}