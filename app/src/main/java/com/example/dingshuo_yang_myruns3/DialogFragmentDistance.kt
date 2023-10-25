package com.example.dingshuo_yang_myruns3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class DialogFragmentDistance : DialogFragment() {

    interface OnDistanceSetListener {
        fun onDistanceSet(distance: Double)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_fragment_distance, null)

        val editText = view.findViewById<EditText>(R.id.distanceEditText)

        builder.setView(view)
            .setTitle("Distance")
            .setPositiveButton("OK") { _, _ ->
                // Safety guard in case the user does not input anything
                val input = editText.text
                if (input.isNotEmpty()) {
                    (activity as? DialogFragmentDistance.OnDistanceSetListener)?.onDistanceSet(
                        input.toString().toDouble()
                    )
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}




