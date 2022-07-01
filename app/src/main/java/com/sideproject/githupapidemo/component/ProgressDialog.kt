package com.sideproject.githupapidemo.component

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.sideproject.githupapidemo.R

class ProgressDialog constructor(context: Context) {

    private var dialog: Dialog? = null

    init {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(R.layout.progress_dialog)
        dialog = alertDialog.create() as Dialog
    }

    fun show(): ProgressDialog {
        dialog?.show()
        return this
    }

    fun dismiss(): ProgressDialog {
        dialog?.dismiss()
        return this
    }

}