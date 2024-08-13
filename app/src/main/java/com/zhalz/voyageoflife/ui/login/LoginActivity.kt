package com.zhalz.voyageoflife.ui.login

import android.content.Intent
import android.os.Bundle
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
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Message.createMessage
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

        observeLogin()
    }

    private fun observeLogin() = lifecycleScope.launch {
        viewModel.loginResponse.collect {
            when(it) {
                is ApiResult.Success -> {
                    val username = it.data?.data?.name
                    createMessage(this@LoginActivity, getString(R.string.login_success, username))
                    toHome()
                    binding.isLoading = false
                }
                is ApiResult.Error -> {
                    createMessage(this@LoginActivity, it.message)
                    binding.isLoading = false
                }
                is ApiResult.Loading -> binding.isLoading = true
            }
        }
    }

    fun toRegister() {
        val toRegister = Intent(this, RegisterActivity::class.java)
        startActivity(toRegister)
        finish()
    }

    private fun toHome() {
        val toHome = Intent(this, HomeActivity::class.java)
        startActivity(toHome)
        finish()
    }

}