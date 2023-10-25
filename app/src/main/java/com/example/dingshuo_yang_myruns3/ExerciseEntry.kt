package com.example.dingshuo_yang_myruns3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import java.util.Calendar

// Reference: The following codes are learned from lecture tutorial
@Entity(tableName = "exercise_entry_table")
@TypeConverters(Converters::class)
data class ExerciseEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "comment_column")
    var comment: String = "",

    @ColumnInfo(name = "input_type_column")
    var inputType: Int = 0,

    @ColumnInfo(name = "activity_type_column")
    var activityType: Int = 0,

    @ColumnInfo(name = "duration_column")
    var duration: Double = 0.0,

    @ColumnInfo(name = "distance_column")
    var distance: Double = 0.0,

    @ColumnInfo(name = "avg_pace_column")
    var avgPace: Double = 0.0,

    @ColumnInfo(name = "avg_speed_column")
    var avgSpeed: Double = 0.0,

    @ColumnInfo(name = "calorie_column")
    var calorie: Double = 0.0,

    @ColumnInfo(name = "climb_column")
    var climb: Double = 0.0,

    @ColumnInfo(name = "heart_rate_column")
    var heartRate: Double = 0.0,

    @ColumnInfo(name = "date_time_column")
    var dateTime: Calendar = Calendar.getInstance(),

    // Not required in MyRuns3:
    //@ColumnInfo(name = "location_list_column")
    @Ignore
    var locationList: ArrayList<LatLng> = ArrayList()

)
