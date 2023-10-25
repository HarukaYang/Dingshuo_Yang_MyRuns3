package com.example.dingshuo_yang_myruns3

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ListView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

// Implement interface to pass obj to dialog fragments learned from: https://stackoverflow.com/questions/71005970/how-to-access-the-properties-of-the-host-activity-from-a-fragment-dialog-kotlin
class ManualInputActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, DialogFragmentDuration.OnDurationSetListener,
    DialogFragmentComment.OnCommentSetListener, DialogFragmentCalorie.OnCalorieSetListener,
    DialogFragmentHeartRate.OnHeartRateSetListener, DialogFragmentDistance.OnDistanceSetListener {

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var listView: ListView
    private lateinit var exerciseEntry: ExerciseEntry

    //DBs
    private lateinit var database: ExerciseEntryDatabase
    private lateinit var databaseDao: ExerciseEntryDatabaseDao
    private lateinit var repository: ExerciseEntryRepository
    private lateinit var viewModelFactory: ExerciseEntryViewModelFactory
    private lateinit var exerciseEntryViewModel: ExerciseEntryViewModel


    companion object {
        private val items =
            arrayOf("Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)
        listView = findViewById(R.id.list_view)

        database = ExerciseEntryDatabase.getInstance(this)
        databaseDao = database.exerciseEntryDatabaseDao
        repository = ExerciseEntryRepository(databaseDao)
        viewModelFactory = ExerciseEntryViewModelFactory(repository)
        exerciseEntryViewModel = ViewModelProvider(this, viewModelFactory)[ExerciseEntryViewModel::class.java]

        // Init exerciseEntry obj
        exerciseEntry = ExerciseEntry()

        // Get selected activity type
        val intent = intent
        val selectedActivityType = intent.getIntExtra("ActivityType", -1)
        exerciseEntry.activityType = selectedActivityType

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            when (selectedItem) {
                "Date" -> {
                    val datePicker = DialogFragmentDatePicker()
                    datePicker.show(supportFragmentManager, "datePicker")
                }

                "Time" -> {
                    val timePicker = DialogFragmentTimePicker()
                    timePicker.show(supportFragmentManager, "timePicker")
                }

                "Duration" -> {
                    val durationDialog = DialogFragmentDuration()
                    durationDialog.show(supportFragmentManager, "durationInput")
                }

                "Distance" -> {
                    val distanceDialog = DialogFragmentDistance()
                    distanceDialog.show(supportFragmentManager, "distanceInput")
                }

                "Calories" -> {
                    val caloriesDialog = DialogFragmentCalorie()
                    caloriesDialog.show(supportFragmentManager, "caloriesInput")
                }

                "Heart Rate" -> {
                    val heartRateDialog = DialogFragmentHeartRate()
                    heartRateDialog.show(supportFragmentManager, "heartRateInput")
                }

                "Comment" -> {
                    val commentDialog = DialogFragmentComment()
                    commentDialog.show(supportFragmentManager, "commentDialog")
                }

            }
        }


        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        // Placeholder buttons as behavior is not required in MyRuns2
        saveButton.setOnClickListener {
            exerciseEntryViewModel.insert(exerciseEntry)
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        exerciseEntry.dateTime.set(Calendar.YEAR, year)
        exerciseEntry.dateTime.set(Calendar.MONTH, month)
        exerciseEntry.dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        exerciseEntry.dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        exerciseEntry.dateTime.set(Calendar.MINUTE, minute)
    }

    override fun onDurationSet(duration: Double) {
        exerciseEntry.duration = duration

    }

    override fun onDistanceSet(distance: Double) {
        exerciseEntry.distance = distance
    }

    override fun onCalorieSet(calorie: Double) {
        exerciseEntry.calorie = calorie
    }

    override fun onHeartRateSet(heartRate: Double) {
        exerciseEntry.heartRate = heartRate
    }

    override fun onCommentSet(comment: String) {
        exerciseEntry.comment = comment
    }
}