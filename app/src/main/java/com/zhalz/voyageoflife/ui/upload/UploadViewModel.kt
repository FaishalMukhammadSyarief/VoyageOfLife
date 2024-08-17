package com.zhalz.voyageoflife.ui.upload

import androidx.lifecycle.ViewModel
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.data.repository.story.StoryRepository
import com.zhalz.voyageoflife.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    suspend fun uploadStory(description: String, image: File): Flow<ApiResult<UploadResponse>> = flow {
        emit(ApiResult.Loading())
        val result = storyRepository.uploadStories(description, image)
        emit(result)
    }

}