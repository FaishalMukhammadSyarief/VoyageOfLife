package com.zhalz.voyageoflife.ui.welcome

import androidx.lifecycle.ViewModel
import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    suspend fun isLogin() = authRepository.isLogin()

}