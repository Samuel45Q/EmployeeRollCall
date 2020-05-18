package com.sam.employeerollcallapplication.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.sam.employeerollcallapplication.R

typealias DialogOnClick = (DialogInterface) -> Unit

object DialogUtil {
    fun showDialog(context: Context, model: DialogModel) {
        showDialog(context, model.message, model.title, model.positiveButton, model.negativeButton, model.onPositiveButtonClick, model.onNegativeButtonClick)
    }

    fun showDialog(context: Context, message: String, title: String? = null,
                   @StringRes positiveButton: Int? = null,
                   @StringRes negativeButton: Int? = null,
                   onPositiveButtonClick: DialogOnClick? = null,
                   onNegativeButtonClick: DialogOnClick? = null) {
        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
            .setCancelable(false)
            .setMessage(message)
        title?.let { builder.setTitle(it) }
        positiveButton?.let {
            builder.setPositiveButton(it) { dialog: DialogInterface, _: Int ->
                onPositiveButtonClick?.invoke(dialog)
            }
        }
        negativeButton?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                onNegativeButtonClick?.invoke(dialog) ?: dialog.dismiss()
            }
        }
        builder.show()
    }

    fun showDialog(context: Context, @StringRes messageId: Int, @StringRes titleId: Int,
                   positiveButton: Pair<Int, DialogOnClick>,
                   negativeButton: Pair<Int, DialogOnClick>) {
        val message = context.getString(messageId)
        val title = context.getString(titleId)

        showDialog(context, message, title,
            positiveButton = positiveButton.first,
            onPositiveButtonClick = positiveButton.second,
            negativeButton = negativeButton.first,
            onNegativeButtonClick = negativeButton.second
        )
    }
}
