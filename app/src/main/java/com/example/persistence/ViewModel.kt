package com.example.persistence

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

const val INITIAL_COUNTER_VALUE = 0

class ViewModel : ViewModel() {

    private var counter: Int = INITIAL_COUNTER_VALUE

    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivity", "OnCleared of CounterViewActivity called")
    }

    private val prefs = PreferencesRepository.getRepository()
    fun saveCounter() {
        viewModelScope.launch {
            prefs.saveCounter(counter)
            Log.d(LOG_TAG, "Saving the counter: $counter")
        }
    }
    fun loadCounter() {
        GlobalScope.launch {
            prefs.counter.collectLatest {
                counter = it
                Log.d(LOG_TAG, "Loaded the counter from DataStore: $counter")
            }
        }
        sleep(1000)
    }

    fun getCount(): Int {
        return this.counter
    }
    fun setCount(c: Int) {
        this.counter = c
        saveCounter()
    }
    fun increaseCount() {
        counter++
        saveCounter()
    }
    fun decreaseCount() {
        counter--
        saveCounter()
    }
}