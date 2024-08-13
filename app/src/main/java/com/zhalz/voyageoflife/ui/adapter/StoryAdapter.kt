package com.zhalz.voyageoflife.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.databinding.ItemStoryBinding

class StoryAdapter(val onItemClick : (StoryData) -> Unit) : ListAdapter<StoryData, StoryAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_story, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(getItem(position)) }
        holder.binding.executePendingBindings()
    }

    inner class UserViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryData>() {
            override fun areItemsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
                return oldItem == newItem
            }
        }
    }

}