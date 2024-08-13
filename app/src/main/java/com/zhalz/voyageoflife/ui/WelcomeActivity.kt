package com.zhalz.voyageoflife.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityWelcomeBinding
import com.zhalz.voyageoflife.ui.login.LoginActivity
import com.zhalz.voyageoflife.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_welcome) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun toLogin() {
        val toLogin = Intent(this, LoginActivity::class.java)
        startActivity(toLogin)
    }

    fun toRegister() {
        val toRegis = Intent(this, RegisterActivity::class.java)
        startActivity(toRegis)
    }

}