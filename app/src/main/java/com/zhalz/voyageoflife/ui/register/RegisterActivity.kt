package com.zhalz.voyageoflife.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityRegisterBinding
import com.zhalz.voyageoflife.ui.login.LoginActivity
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Message.createMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_register) }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.activity = this
        binding.viewmodel = viewModel

        observeRegister()
    }

    private fun observeRegister() = lifecycleScope.launch {
        viewModel.registerResponse.collect {
            when(it) {
                is ApiResult.Success -> {
                    createMessage(this@RegisterActivity, getString(R.string.register_success, it.data?.message))
                    binding.isLoading = false
                    toLogin()
                }
                is ApiResult.Error -> {
                    createMessage(this@RegisterActivity, it.message)
                    binding.isLoading = false
                }
                is ApiResult.Loading -> binding.isLoading = true
            }
        }
    }

    fun toLogin() {
        val toLogin = Intent(this, LoginActivity::class.java)
        startActivity(toLogin)
        finish()
    }

}