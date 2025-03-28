package com.example.persistence

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG = "MainActivity"
const val COUNTER_KEY = "counter"

class MainActivity : AppCompatActivity() {

    private lateinit var counterText: TextView
    private lateinit var increaseButton: Button
    private var saveButton: Button? = null
    private var loadButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        counterText = findViewById(R.id.counter_text)
        counterText.text = viewModel.getCount().toString()

        increaseButton = findViewById(R.id.button)
        increaseButton.setOnClickListener {
            viewModel.increaseCount()
            counterText.text = viewModel.getCount().toString()
        }

        saveButton = findViewById(R.id.button_save)
        saveButton?.setOnClickListener {
            viewModel.saveCounter()
        }

        loadButton = findViewById(R.id.button_load)
        loadButton?.setOnClickListener {
            viewModel.loadCounter()
            counterText.text = viewModel.getCount().toString()
        }
//      Other ways (besides using ?.) of preventing a crash if this button does not exist in portrait mode:
//      if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//          ...
//      }
//      if (this::loadButton.isInitialized) {
//          ...
//      }

//        onBackPressedDispatcher.addCallback(
//            this /* lifecycle owner */,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    viewModel.saveCounter()
//                    finish()
//                }
//            }
//        )

//        viewModel.setCount(
//            savedInstanceState?.getInt(COUNTER_KEY, INITIAL_COUNTER_VALUE) ?: INITIAL_COUNTER_VALUE
//        )
//        Log.d(LOG_TAG, "The counter value is restored using savedInstanceState to ${viewModel.getCount()}")

        viewModel.loadCounter()
        Log.d(LOG_TAG, "setting counter after loading it from DataStore to ${viewModel.getCount()}")
        counterText.text = viewModel.getCount().toString()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy called")
    }
    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "The counter value is saved")
        outState.putInt(COUNTER_KEY, viewModel.getCount())
    }

    private val viewModel: ViewModel by lazy {
        PreferencesRepository.initialize(this)
        ViewModelProvider(this)[ViewModel::class.java]
    }
}