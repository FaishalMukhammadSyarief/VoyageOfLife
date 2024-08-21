package com.zhalz.voyageoflife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.databinding.ItemStoryBinding

class StoryAdapter(val toDetail : (StoryData) -> Unit) : PagingDataAdapter<StoryData, StoryAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_story, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val storyData = getItem(position) ?: return
        holder.bind(storyData)
        holder.itemView.setOnClickListener { toDetail(storyData) }
    }

    inner class UserViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storyData: StoryData) {
            binding.data = storyData
            binding.executePendingBindings()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryData>() {
            override fun areItemsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}