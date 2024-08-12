package com.zhalz.voyageoflife.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityLoginBinding
import com.zhalz.voyageoflife.ui.RegisterActivity
import com.zhalz.voyageoflife.utils.Message.createMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_login) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.activity = this
        binding.viewmodel = viewModel

        initUI()
        observeLogin()
    }

    private fun initUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.apply { statusBarColor = getColor(R.color.green) }
    }

    private fun observeLogin() = lifecycleScope.launch {
        viewModel.loginResponse.collect {
            if (it.error == false) createMessage(this@LoginActivity, getString(R.string.login_success, it.data?.name))
            else createMessage(this@LoginActivity, it.message)
        }
    }

    fun toRegister() {
        val toRegister = Intent(this, RegisterActivity::class.java)
        startActivity(toRegister)
        finish()
    }

}