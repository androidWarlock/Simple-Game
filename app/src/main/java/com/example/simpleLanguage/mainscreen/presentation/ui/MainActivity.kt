package com.example.simpleLanguage.mainscreen.presentation.ui

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.simpleLanguage.R
import com.example.simpleLanguage.common.ResourceUtils
import com.example.simpleLanguage.common.SGApplication
import com.example.simpleLanguage.common.Status
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
                        Log.d("TEST", it.data.toString())
                        //LoadData and start the game
                    } else {
                        //Show Error Message
                    }
                }
            }
        })
    }


    fun rightClicked(view: View) {


    }

    fun wrongClicked(view: View) {

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


}
