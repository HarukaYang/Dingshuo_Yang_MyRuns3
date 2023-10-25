package com.example.dingshuo_yang_myruns3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment

class FragmentStart : Fragment() {
    private lateinit var startButton: Button
    private lateinit var inputTypeSpinner: Spinner
    private lateinit var activityTypeSpinner: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    // After view is created, performs the logic operations
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton = view.findViewById(R.id.start_button)
        inputTypeSpinner = view.findViewById(R.id.spinner_input_type)
        activityTypeSpinner = view.findViewById(R.id.spinner_activity_type)

        startButton.setOnClickListener {
            val selectedActivityType = activityTypeSpinner.selectedItem.toString()

            val intent = when (inputTypeSpinner.selectedItem.toString()) {
                "Manual Entry" -> Intent(activity, ManualInputActivity::class.java)
                "GPS", "Automatic" -> Intent(activity, MapDisplayActivity::class.java)
                // Show mainActivity if error occurs that no entry is matched (although it is unlikely).
                else -> Intent(activity, MainActivity::class.java)
            }

            val activityTypeInt: Int = when (selectedActivityType) {
                "Running" -> 0
                "Walking" -> 1
                "Standing" -> 2
                "Cycling" -> 3
                "Hiking" -> 4
                "Downhill Skiing" -> 5
                "Cross-Country Skiing" -> 6
                "Snowboarding" -> 7
                "Skating" -> 8
                "Swimming" -> 9
                "Mountain Biking" -> 10
                "Wheelchair" -> 11
                "Elliptical" -> 12
                "Others" -> 13
                else -> -1
            }

            intent.putExtra("ActivityType", activityTypeInt)
            startActivity(intent)
        }
    }
}



