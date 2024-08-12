package com.zhalz.voyageoflife.utils

import android.content.Context
import android.widget.Toast

object Message {

    fun createMessage(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}