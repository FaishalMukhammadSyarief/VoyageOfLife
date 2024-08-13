package com.zhalz.voyageoflife.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.databinding.ActivityHomeBinding
import com.zhalz.voyageoflife.ui.adapter.StoryAdapter
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Message.createMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_home) }
    private val viewModel: HomeViewModel by viewModels()
    private val storyAdapter: StoryAdapter by lazy { StoryAdapter { toDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        binding.storyAdapter = storyAdapter

        initUI()
        collectStories()
    }

    private fun initUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun collectStories() = lifecycleScope.launch {
        viewModel.storiesResponse.collect {
            when (it) {
                is ApiResult.Success -> {
                    storyAdapter.submitList(it.data?.listStory)
                    binding.isLoading = false
                }
                is ApiResult.Error -> {
                    createMessage(this@HomeActivity, it.message)
                    binding.isLoading = false
                }
                is ApiResult.Loading -> binding.isLoading = true
            }
        }
    }

    private fun toDetail(data: StoryData) {
        createMessage(this, data.description)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getStories()
    }

}