package com.example.dingshuo_yang_myruns3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Reference: The following codes are learned from lecture tutorial

@Database(entities = [ExerciseEntry::class], version = 1)
abstract class ExerciseEntryDatabase : RoomDatabase() { //XD: Room automatically generates implementations of your abstract ExerciseEntryDatabase class.
    abstract val exerciseEntryDatabaseDao: ExerciseEntryDatabaseDao

    companion object{
        //The Volatile keyword guarantees visibility of changes to the INSTANCE variable across threads
        @Volatile
        private var INSTANCE: ExerciseEntryDatabase? = null

        fun getInstance(context: Context) : ExerciseEntryDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        ExerciseEntryDatabase::class.java, "exercise_entry_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}