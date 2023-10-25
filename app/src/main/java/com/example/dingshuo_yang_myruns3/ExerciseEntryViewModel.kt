package com.example.dingshuo_yang_myruns3

import androidx.lifecycle.*

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExerciseEntryViewModel(private val repository: ExerciseEntryRepository) : ViewModel() {
    val allExerciseEntriesLiveData: LiveData<List<ExerciseEntry>> =
        repository.allExerciseEntries.asLiveData()

    fun insert(exerciseEntry: ExerciseEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(exerciseEntry)
        }
    }

    fun deleteFirst() {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseEntryList = allExerciseEntriesLiveData.value
            if (exerciseEntryList != null && exerciseEntryList.size > 0) {
                val id = exerciseEntryList[0].id
                repository.delete(id)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseEntryList = allExerciseEntriesLiveData.value
            if (exerciseEntryList != null && exerciseEntryList.size > 0)
                repository.deleteAll()
        }
    }
}

class ExerciseEntryViewModelFactory(private val repository: ExerciseEntryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseEntryViewModel::class.java))
            return ExerciseEntryViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}