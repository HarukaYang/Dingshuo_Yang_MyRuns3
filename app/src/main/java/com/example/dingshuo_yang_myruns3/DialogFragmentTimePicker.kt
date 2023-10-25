package com.example.dingshuo_yang_myruns3

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener

class DialogFragmentTimePicker : DialogFragment() {
    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            requireActivity(), requireActivity() as OnTimeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), true
        )
    }
}
