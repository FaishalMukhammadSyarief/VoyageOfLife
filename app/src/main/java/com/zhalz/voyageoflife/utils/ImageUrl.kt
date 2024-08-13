package com.zhalz.voyageoflife.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageUrl {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadUrl(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .into(this)
    }
}