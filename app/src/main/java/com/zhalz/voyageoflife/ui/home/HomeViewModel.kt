package com.zhalz.voyageoflife.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import com.zhalz.voyageoflife.data.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val storyRepository: StoryRepository, private val authRepository: AuthRepository): ViewModel() {

    suspend fun getPagingStories() = storyRepository.getPagingStories()

    fun clearUserCredential() = viewModelScope.launch {
        authRepository.deleteUser()
    }

}