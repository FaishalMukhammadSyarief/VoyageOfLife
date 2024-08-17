package com.zhalz.voyageoflife.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat

object ActivityOpener {

    inline fun <reified T : Activity> Context.openActivity(
        finish: Boolean = false,
        finishAll: Boolean = false,
        extraData: Intent.() -> Unit = {}
    ) {
        val destination = Intent(this, T::class.java).apply(extraData)
        val fadeTransition = ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity)
        startActivity(destination, fadeTransition.toBundle())

        if (finish) finish()
        if (finishAll) finishAffinity()
    }

}