package com.zhalz.voyageoflife.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_welcome) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun toLogin() {

    }

    fun toRegister() {

    }

}