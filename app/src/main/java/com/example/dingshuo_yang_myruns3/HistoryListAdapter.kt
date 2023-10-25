package com.example.dingshuo_yang_myruns3

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Locale

// Some of the following codes for DB operations are learned from lecture

class HistoryListAdapter(
    private val context: Context,
    private var exerciseEntryList: List<ExerciseEntry>
) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return exerciseEntryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return exerciseEntryList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.layout_adapter, null)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val unitInUse = sharedPreferences.getString("unit_preferences_dialog", "0")

        val textViewEntryLabel = view.findViewById(R.id.tv_entry_label) as TextView
        val textViewDistanceDuration = view.findViewById(R.id.tv_distance_duration) as TextView

        val currentEntry = exerciseEntryList[position]
        // Map the data values to view texts
        val inputType: String = when (currentEntry.inputType) {
            0 -> "Manual Entry:"
            1 -> "GPS:"
            2 -> "Automatic:"
            else -> "Unknown"
        }

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

        val formatter = SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.getDefault())
        val formattedDateTime: String = formatter.format(currentEntry.dateTime.time)

        // Concatenate strings to form the entry titles
        val entryLabel = "$inputType $activityType $formattedDateTime"
        textViewEntryLabel.text = entryLabel

        val distance = when(unitInUse) {
            "0" -> (currentEntry.distance*1.60934).toString() + " Kilometers"
            "1" -> currentEntry.distance.toString() + " Miles"
            else-> "Unknown"
        }
        val duration = currentEntry.duration
        val minutes = duration.toInt()
        val seconds = ((duration - minutes) * 60).toInt()
        val formattedDuration = when {
            // Show both mins and secs if duration is longer than 1 min
            duration >= 1 -> "$minutes mins, $seconds secs"

            // Else only show seconds
            else -> "$seconds secs"
        }
        textViewDistanceDuration.text = "$distance, $formattedDuration"

        view.setOnClickListener {
            val intent = Intent(context, DisplayEntryActivity::class.java)
            intent.putExtra("entryIndex", position)
            context.startActivity(intent)
        }
        return view
    }

    fun replace(newCommentList: List<ExerciseEntry>) {
        exerciseEntryList = newCommentList
    }

}