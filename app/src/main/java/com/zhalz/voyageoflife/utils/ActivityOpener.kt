package com.zhalz.voyageoflife.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

object ActivityOpener {

    inline fun <reified T : Activity> Context.openActivity(
        finish: Boolean = false,
        finishAll: Boolean = false,
        extraData: Intent.() -> Unit = {}
    ) {
        val destination = Intent(this, T::class.java).apply(extraData)
        startActivity(destination)

        if (this !is Activity) return
        if (finish) finish()
        if (finishAll) finishAffinity()
    }

}