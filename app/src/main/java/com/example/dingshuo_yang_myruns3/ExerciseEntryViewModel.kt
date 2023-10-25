package com.example.dingshuo_yang_myruns3

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// Reference: Some of the following codes are learned and derived from lecture tutorial
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
            if (!exerciseEntryList.isNullOrEmpty()) {
                val id = exerciseEntryList[0].id
                repository.delete(id)
            }
        }
    }

    fun deleteEntry(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseEntryList = allExerciseEntriesLiveData.value
            if (!exerciseEntryList.isNullOrEmpty()) {
                val id = exerciseEntryList[position].id
                repository.delete(id)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseEntryList = allExerciseEntriesLiveData.value
            if (!exerciseEntryList.isNullOrEmpty())
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