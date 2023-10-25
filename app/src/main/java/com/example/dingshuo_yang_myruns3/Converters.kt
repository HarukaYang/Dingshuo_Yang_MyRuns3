package com.example.dingshuo_yang_myruns3

import androidx.room.TypeConverter
import java.util.Calendar

// Converter learned from: https://stackoverflow.com/questions/50313525/room-using-date-field
object Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}