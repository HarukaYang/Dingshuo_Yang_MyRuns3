package com.example.dingshuo_yang_myruns3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class FragmentHistory : Fragment() {
    private lateinit var myListView: ListView

    private lateinit var arrayList: ArrayList<ExerciseEntry>
    private lateinit var arrayAdapter: HistoryListAdapter

    // Reference: The following DB codes are learned and derived from lecture tutorial
    private lateinit var database: ExerciseEntryDatabase
    private lateinit var databaseDao: ExerciseEntryDatabaseDao
    private lateinit var repository: ExerciseEntryRepository
    private lateinit var viewModelFactory: ExerciseEntryViewModelFactory
    private lateinit var exerciseEntryViewModel: ExerciseEntryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        myListView = view.findViewById(R.id.history_list)

        arrayList = ArrayList()

        arrayAdapter = HistoryListAdapter(requireActivity(), arrayList)
        myListView.adapter = arrayAdapter

        database = ExerciseEntryDatabase.getInstance(requireActivity())
        databaseDao = database.exerciseEntryDatabaseDao
        repository = ExerciseEntryRepository(databaseDao)
        viewModelFactory = ExerciseEntryViewModelFactory(repository)
        exerciseEntryViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[ExerciseEntryViewModel::class.java]

        exerciseEntryViewModel.allExerciseEntriesLiveData.observe(
            requireActivity(),
            Observer { it ->
                arrayAdapter.replace(it)
                arrayAdapter.notifyDataSetChanged()
            })

        return view
    }

    override fun onResume() {
        super.onResume()
        // Force the adapter to refresh the list
        arrayAdapter.notifyDataSetChanged()
    }
}