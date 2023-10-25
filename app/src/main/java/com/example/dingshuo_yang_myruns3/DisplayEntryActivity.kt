package com.example.dingshuo_yang_myruns3

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Locale

class DisplayEntryActivity : AppCompatActivity() {

    private lateinit var inputTypeEditText: EditText
    private lateinit var activityTypeEditText: EditText
    private lateinit var dateTimeEditText: EditText
    private lateinit var durationEditText: EditText
    private lateinit var distanceEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var heartRateEditText: EditText

    //DBs
    private lateinit var database: ExerciseEntryDatabase
    private lateinit var databaseDao: ExerciseEntryDatabaseDao
    private lateinit var repository: ExerciseEntryRepository
    private lateinit var viewModelFactory: ExerciseEntryViewModelFactory
    private lateinit var exerciseEntryViewModel: ExerciseEntryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_entry)

        database = ExerciseEntryDatabase.getInstance(this)
        databaseDao = database.exerciseEntryDatabaseDao
        repository = ExerciseEntryRepository(databaseDao)
        viewModelFactory = ExerciseEntryViewModelFactory(repository)
        exerciseEntryViewModel =
            ViewModelProvider(this, viewModelFactory)[ExerciseEntryViewModel::class.java]

        inputTypeEditText = findViewById(R.id.input_type_edit_text)
        activityTypeEditText = findViewById(R.id.activity_type_edit_text)
        dateTimeEditText = findViewById(R.id.date_time_edit_text)
        durationEditText = findViewById(R.id.duration_edit_text)
        distanceEditText = findViewById(R.id.distance_edit_text)
        caloriesEditText = findViewById(R.id.calories_edit_text)
        heartRateEditText = findViewById(R.id.heart_rate_edit_text)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val unitInUse = sharedPreferences.getString("unit_preferences_dialog", "0")
        val position = intent.getIntExtra("entryIndex", -1)

        exerciseEntryViewModel.allExerciseEntriesLiveData.observe(
            this,
            Observer { exerciseEntryList ->
                val currentEntry = exerciseEntryList[position]
                // Map the data values to view texts
                val inputType: String = when (currentEntry.inputType) {
                    0 -> "Manual Entry"
                    1 -> "GPS"
                    2 -> "Automatic"
                    else -> "Unknown"
                }
                inputTypeEditText.setText(inputType)

                val activityType: String = when (currentEntry.activityType) {
                    0 -> "Running"
                    1 -> "Walking"
                    2 -> "Standing"
                    3 -> "Cycling"
                    4 -> "Hiking"
                    5 -> "Downhill Skiing"
                    6 -> "Cross-Country Skiing"
                    7 -> "Snowboarding"
                    8 -> "Skating"
                    9 -> "Swimming"
                    10 -> "Mountain Biking"
                    11 -> "Wheelchair"
                    12 -> "Elliptical"
                    13 -> "Other"
                    else -> "Unknown"
                }
                activityTypeEditText.setText(activityType)

                val formatter = SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.getDefault())
                val formattedDateTime: String = formatter.format(currentEntry.dateTime.time)
                dateTimeEditText.setText(formattedDateTime)

                // Concatenate strings to form the entry titles
                val entryLabel = "$inputType $activityType $formattedDateTime"
                dateTimeEditText.setText(entryLabel)

                val distance = when(unitInUse) {
                    "0" -> (currentEntry.distance*1.60934).toString() + " Kilometers"
                    "1" -> currentEntry.distance.toString() + " Miles"
                    else-> "Unknown"
                }
                distanceEditText.setText(distance)

                val duration = currentEntry.duration

                val minutes = duration.toInt()
                val seconds = ((duration - minutes) * 60).toInt()
                val formattedDuration = when {
                    // Show both mins and secs if duration is longer than 1 min
                    duration >= 1 -> "$minutes mins, $seconds secs"
                    // Else only show seconds
                    else -> "$seconds secs"
                }
                durationEditText.setText(formattedDuration)

                val calories = currentEntry.calorie.toString() + " cals"
                caloriesEditText.setText(calories)

                val heartRate = currentEntry.heartRate.toString() + " bpm"
                heartRateEditText.setText(heartRate)
            })


    }

}