package com.example.dingshuo_yang_myruns3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class DialogFragmentHeartRate : DialogFragment() {

    interface OnHeartRateSetListener {
        fun onHeartRateSet(heartRate: Double)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_fragment_heart_rate, null)

        val editText = view.findViewById<EditText>(R.id.heartRateEditText)

        builder.setView(view)
            .setTitle("HeartRate")
            .setPositiveButton("OK") { _, _ ->
                // Safety guard in case the user does not input anything
                val input = editText.text
                if (input.isNotEmpty()) {
                    if (input.toString() == ".") {
                        (activity as? OnHeartRateSetListener)?.onHeartRateSet(
                            0.0
                        )
                    } else {
                        (activity as? OnHeartRateSetListener)?.onHeartRateSet(
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




