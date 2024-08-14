package com.zhalz.voyageoflife.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityWelcomeBinding
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_welcome) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        binding.login = Intent(this, HomeActivity::class.java)
        binding.register = Intent(this, RegisterActivity::class.java)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

}