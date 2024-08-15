package com.zhalz.voyageoflife.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zhalz.voyageoflife.R

object ImageUrl {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadUrl(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.goblin)
            .error(R.drawable.goblin)
            .into(this)
    }
}