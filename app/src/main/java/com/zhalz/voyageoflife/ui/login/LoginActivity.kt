package com.zhalz.voyageoflife.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityLoginBinding
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.ui.register.RegisterActivity
import com.zhalz.voyageoflife.utils.ActivityOpener.openActivity
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.ToastMaker.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_login) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.activity = this
        binding.viewmodel = viewModel

        playAnimation()
        observeLogin()
    }

    private fun playAnimation() = AnimatorSet().apply {
        playSequentially(
            fadeAnim(binding.tvTitle, 300),
            fadeAnim(binding.edLoginEmail, 250),
            fadeAnim(binding.edlLoginEmail, 250),
            fadeAnim(binding.edLoginPassword, 250),
            fadeAnim(binding.edlLoginPassword, 250),
            fadeAnim(binding.btnLogin, 250),
            fadeAnim(binding.linearRegister, 250)
        )
        start()
    }

    private fun fadeAnim(view: View, duration: Long) =
        ObjectAnimator.ofFloat(view, View.ALPHA, 1f).setDuration(duration)

    private fun observeLogin() = lifecycleScope.launch {
        viewModel.loginResponse.collect {
            when(it) {
                is ApiResult.Success -> {
                    toast(R.string.login_success, it.data?.data?.name)
                    binding.isLoading = false
                    openActivity<HomeActivity>(finishAll = true)
                }
                is ApiResult.Error -> {
                    toast(it.message)
                    binding.isLoading = false
                }
                is ApiResult.Loading -> binding.isLoading = true
            }
        }
    }

    fun toRegister() {
        openActivity<RegisterActivity>(true)
    }

}