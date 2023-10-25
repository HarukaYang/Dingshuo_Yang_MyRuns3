package com.example.dingshuo_yang_myruns3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment



class DialogFragmentDuration : DialogFragment() {

    interface OnDurationSetListener {
        fun onDurationSet(duration: Double)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_fragment_duration, null)

        val editText = view.findViewById<EditText>(R.id.durationEditText)

        builder.setView(view)
            .setTitle("Duration")
            .setPositiveButton("OK") { _, _ ->
                // Safety guard in case the user does not input anything
                val input = editText.text
                // Learned from: https://stackoverflow.com/questions/71005970/how-to-access-the-properties-of-the-host-activity-from-a-fragment-dialog-kotlin
                if (input.isNotEmpty()) {
                    if (input.toString() == ".") {
                        (activity as? OnDurationSetListener)?.onDurationSet(0.0)
                    } else {
                        (activity as? OnDurationSetListener)?.onDurationSet(
                            input.toString().toDouble()
                        )
                    }
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}




