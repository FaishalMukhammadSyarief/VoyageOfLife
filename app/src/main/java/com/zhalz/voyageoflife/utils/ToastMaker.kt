package com.zhalz.voyageoflife.utils

import android.content.Context
import android.widget.Toast

object ToastMaker {

    fun Context.toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.toast(message: Int, p1: String? = null, p2: String? = null, p3: String? = null) {
        Toast.makeText(this, getString(message, p1, p2, p3), Toast.LENGTH_SHORT).show()
    }

}