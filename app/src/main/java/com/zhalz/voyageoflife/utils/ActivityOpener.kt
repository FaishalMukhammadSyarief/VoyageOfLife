package com.zhalz.voyageoflife.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat

object ActivityOpener {

    inline fun <reified T : Activity> Context.openActivity(
        finish: Boolean = false,
        finishAll: Boolean = false,
        transition: ActivityOptionsCompat? = null,
        extraData: Intent.() -> Unit = {},
    ) {
        val destination = Intent(this, T::class.java).apply(extraData)
        startActivity(destination, transition?.toBundle())

        if (this !is Activity) return
        if (finish) finish()
        if (finishAll) finishAffinity()
    }

}