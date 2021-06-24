package com.example.mobilliumtask.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainActBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActBinding.root)

        initSpeechToTextListener()
        initSearchBar()
    }

    fun initSearchBar(){
        mainActBinding.searchView.setOnClickListener {
            mainActBinding.searchView.isIconified = false
        }
    }

    fun initSpeechToTextListener() {
        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    mainActBinding.searchView.isIconified = false
                    mainActBinding.searchView.requestFocusFromTouch()
                    val resultText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                    mainActBinding.searchView.setQuery(resultText, false)
                }
            }

        mainActBinding.btnSpeechToText.setOnClickListener {

            var speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text")
            resultLauncher.launch(speechIntent)
        }
    }

}