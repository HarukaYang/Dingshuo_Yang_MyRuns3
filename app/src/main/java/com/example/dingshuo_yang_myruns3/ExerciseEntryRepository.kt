package com.example.dingshuo_yang_myruns3

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// Reference: Some of the following codes are learned and derived from lecture tutorial and practice code examples
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