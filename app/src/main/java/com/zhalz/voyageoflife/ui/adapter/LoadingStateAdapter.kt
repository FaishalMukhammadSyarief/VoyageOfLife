package com.zhalz.voyageoflife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ItemLoadingBinding

class LoadingStateAdapter(val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder =
        LoadingStateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_loading, parent, false))

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadingStateViewHolder(private val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> binding.isLoading = true
                is LoadState.NotLoading -> binding.isLoading = false
                is LoadState.Error -> {
                    binding.errorMsg.text = loadState.error.localizedMessage
                    binding.isLoading = false
                }
            }
            binding.retryButton.setOnClickListener { retry.invoke() }
            binding.executePendingBindings()
        }
    }

}