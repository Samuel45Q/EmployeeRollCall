package com.sam.employeerollcallapplication.utils

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.utils.ColorUtil.Companion.getColorFromStyle

class EmployeeRollLoadingDialog(context: Context) {
    private var dialog: Dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

        val progressBar = dialog.findViewById<ProgressBar>(R.id.progress_bar_dialog)
        progressBar.indeterminateTintList =
            ColorStateList.valueOf(getColorFromStyle(context, android.R.attr.colorAccent))
    }

    fun show(message: String = "") {
        val dialogMessageTextView = dialog.findViewById<TextView>(R.id.text_loading_dialog_message)
        dialogMessageTextView?.text = message
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }
}