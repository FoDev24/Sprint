package com.androiddevs.Sprint.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.androiddevs.Sprint.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTtackingDialog : DialogFragment() {

    private var yesListener :(() -> Unit)? = null

    fun setYesListener(listener : () ->Unit){
         yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return  MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel the Run?")
            .setMessage("Are your sure to cancel the current run and delete all it's data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") { _, _ ->
                yesListener?.let { yes ->
                    yes()
                }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}