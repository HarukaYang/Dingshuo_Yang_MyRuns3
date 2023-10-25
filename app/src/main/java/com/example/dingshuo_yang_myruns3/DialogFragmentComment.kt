package com.example.dingshuo_yang_myruns3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class DialogFragmentComment : DialogFragment() {
    interface OnCommentSetListener {
        fun onCommentSet(comment: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_fragment_comment, null)

        val editText = view.findViewById<EditText>(R.id.commentEditText)

        builder.setView(view)
            .setTitle("Comment")
            .setPositiveButton("OK") { _, _ ->
                // Safety guard in case the user does not input anything
                val input = editText.text
                if (input.isNotEmpty()) {
                    (activity as? DialogFragmentComment.OnCommentSetListener)?.onCommentSet(input.toString())
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}




