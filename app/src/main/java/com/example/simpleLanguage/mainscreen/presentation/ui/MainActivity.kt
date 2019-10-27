package com.example.simpleLanguage.mainscreen.presentation.ui

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.simpleLanguage.R
import com.example.simpleLanguage.common.ResourceUtils
import com.example.simpleLanguage.common.SGApplication
import com.example.simpleLanguage.common.Status
import com.example.simpleLanguage.common.di.SGConstants.RESET_INTERVAL
import com.example.simpleLanguage.common.di.SGConstants.START_INTERVAL
import com.example.simpleLanguage.common.di.SGConstants.UPDATE_INTERVAL
import com.example.simpleLanguage.mainscreen.di.DaggerMainScreenComponent
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModel
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainScreenViewModelFactory
    private lateinit var viewModel: MainScreenViewModel

    private val updateGameAnimationHandler = Handler()
    private lateinit var updateGameAnimationRunnable: Runnable

    private var isGameStarted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainScreenComponent
            .builder()
            .appComponent((applicationContext as SGApplication).mAppComponent).build()
            .inject(this)


        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainScreenViewModel::class.java)

        loadWords()
        viewModel.getWordsList()
    }

    private fun loadWords() {
        viewModel.words.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    //LoadingView
                }

                Status.ERROR,
                Status.NETWORK_ERROR -> {
                    //Show Error Message
                }

                Status.SUCCESS -> {
                    if (it != null) {
                        startGame()
                        //LoadData and start the game
                    } else {
                        //Show Error Message
                    }
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        if (isGameStarted)
            updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)
    }


    override fun onPause() {
        super.onPause()
        updateGameAnimationHandler.removeCallbacks(updateGameAnimationRunnable)
    }

    fun rightClicked(view: View) {


    }

    fun wrongClicked(view: View) {

    }


    private fun setUpGameAnimationRunnable() {
        updateGameAnimationRunnable = Runnable {
            run {
                //Update UI
                if (!isWordStillVisible()) {

                    resetRound()
                    return@run
                }
                moveWord()
                // Re-run it after the update interval
                updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, UPDATE_INTERVAL)
            }

        }
    }

    // a method to start the game
    private fun startGame(){
        isGameStarted = true
        updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)
    }


    // Animation of the moving word
    private fun moveWord() {
        moving_word_textView.animate().translationY(ResourceUtils.getScreenHeight().toFloat()).setDuration(UPDATE_INTERVAL)
    }

    // A method that returns whether the word ist off screen or not
    private fun isWordStillVisible(): Boolean {
        val screenBound = Rect(0, 0, ResourceUtils.getScreenwidth(), ResourceUtils.getScreenHeight())

        return moving_word_textView.getLocalVisibleRect(screenBound)
    }

    // A method that resets the round either if user answer or word gets off screen
    private fun resetRound() {
        updateGameAnimationHandler.removeCallbacks(updateGameAnimationRunnable)
        moving_word_textView.clearAnimation()
        moving_word_textView.animate().y(-0f).duration = RESET_INTERVAL
        startNextRound()
    }

    // A method that starts next round
    private fun startNextRound() {
        updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)

    }

}
