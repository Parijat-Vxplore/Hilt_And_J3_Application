package com.example.hiltapplication


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject




@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: SomeRepository
) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    init {

        loadData()
    }

    private fun loadData() {
        _data.value = repository.fetchData()
    }

}
