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
import com.zhalz.voyageoflife.ui.detail.DetailActivity
import com.zhalz.voyageoflife.utils.ActivityOpener.openActivity
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Const.Parcel.EXTRA_USER
import com.zhalz.voyageoflife.utils.ToastMaker.toast
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

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getStories()
        }
    }

    private fun collectStories() = lifecycleScope.launch {
        viewModel.storiesResponse.collect {
            when (it) {
                is ApiResult.Success -> {
                    storyAdapter.submitList(it.data?.listStory)
                    binding.swipeRefresh.isRefreshing = false
                }
                is ApiResult.Error -> {
                    toast(it.message)
                    binding.swipeRefresh.isRefreshing = false
                }
                is ApiResult.Loading -> binding.swipeRefresh.isRefreshing = true
            }
        }
    }

    private fun toDetail(data: StoryData) =
        openActivity<DetailActivity> { putExtra(EXTRA_USER, data) }

    override fun onStart() {
        super.onStart()
        viewModel.getStories()
    }

}