package com.example.dingshuo_yang_myruns3

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class MapDisplayActivity : Activity() {

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_display)

        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        // Placeholder buttons as behavior is not required in MyRuns2
        saveButton.setOnClickListener {
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }

    }
}