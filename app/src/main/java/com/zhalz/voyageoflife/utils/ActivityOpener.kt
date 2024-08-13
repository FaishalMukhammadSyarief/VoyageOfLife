package com.zhalz.voyageoflife.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

object ActivityOpener {

    fun Context.openActivity(
        activity: Class<out Activity>,
        finish: Boolean = false,
        finishAll: Boolean = false
    ) {
        val destination = Intent(this, activity)
        startActivity(destination)

        if (this !is Activity) return
        if (finish) finish()
        if (finishAll) finishAffinity()
    }

}