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

class StoryAdapter(val onItemClick : (StoryData) -> Unit) : PagingDataAdapter<StoryData, StoryAdapter.UserViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_story, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val storyData = getItem(position) ?: return
        holder.binding.data = storyData
        holder.itemView.setOnClickListener { onItemClick(storyData) }
        holder.binding.executePendingBindings()
    }

    inner class UserViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<StoryData>() {
            override fun areItemsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem == newItem
            }
        }
    }

}