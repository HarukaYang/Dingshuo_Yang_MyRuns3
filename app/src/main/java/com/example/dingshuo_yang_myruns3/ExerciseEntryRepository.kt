package com.example.dingshuo_yang_myruns3

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//A Repository manages queries and allows you to use multiple backends.
// In the most common example, the Repository implements the logic for
// deciding whether to fetch data from a network or use results cached in a local database.
class ExerciseEntryRepository(private val exerciseEntryDatabaseDao: ExerciseEntryDatabaseDao) {

    val allExerciseEntries: Flow<List<ExerciseEntry>> = exerciseEntryDatabaseDao.getAllExerciseEntries()

    fun insert(exerciseEntry: ExerciseEntry){
        CoroutineScope(IO).launch{
            exerciseEntryDatabaseDao.insertExerciseEntry(exerciseEntry)
        }
    }

    fun delete(id: Long){
        CoroutineScope(IO).launch {
            exerciseEntryDatabaseDao.deleteExerciseEntry(id)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            exerciseEntryDatabaseDao.deleteAll()
        }
    }
}