package com.zhalz.voyageoflife.ui.welcome

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityWelcomeBinding
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.ui.login.LoginActivity
import com.zhalz.voyageoflife.ui.register.RegisterActivity
import com.zhalz.voyageoflife.utils.ActivityOpener.openActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_welcome) }
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)

        checkLogin()
    }

    private fun checkLogin() = lifecycleScope.launch {
        if (viewModel.isLogin()) openActivity<HomeActivity>(finish = true)
    }

    fun toLogin() = openActivity<LoginActivity>()
    fun toRegister() = openActivity<RegisterActivity>()

}