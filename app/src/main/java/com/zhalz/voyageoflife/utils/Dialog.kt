package com.zhalz.voyageoflife.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhalz.voyageoflife.R

object Dialog {

    fun Context.showDialog(
        title: String,
        message: String,
        positiveAction: () -> Unit = {}
    ) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_positive) { dialog, _ ->
                positiveAction()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.action_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}