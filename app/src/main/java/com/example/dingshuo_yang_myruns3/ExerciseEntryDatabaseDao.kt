package com.example.dingshuo_yang_myruns3

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


// Reference: The following codes are learned and derived from lecture tutorials with custom changes.
@Dao
interface ExerciseEntryDatabaseDao {

    @Insert
    suspend fun insertExerciseEntry(exerciseEntry: ExerciseEntry)

    @Query("SELECT * FROM exercise_entry_table")
    fun getAllExerciseEntries(): Flow<List<ExerciseEntry>>

    @Query("DELETE FROM exercise_entry_table")
    suspend fun deleteAll()

    @Query("DELETE FROM exercise_entry_table WHERE id = :key") //":" indicates that it is a Bind variable
    suspend fun deleteExerciseEntry(key: Long)
}