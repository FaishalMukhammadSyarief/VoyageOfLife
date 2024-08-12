package com.zhalz.voyageoflife.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityRegisterBinding
import com.zhalz.voyageoflife.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_register) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.activity = this

        initUI()
    }

    private fun initUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.apply { statusBarColor = getColor(R.color.green) }
    }

    fun toLogin() {
        val toLogin = Intent(this, LoginActivity::class.java)
        startActivity(toLogin)
        finish()
    }

}