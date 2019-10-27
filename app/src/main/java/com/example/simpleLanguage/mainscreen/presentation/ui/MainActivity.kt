package com.example.simpleLanguage.mainscreen.presentation.ui

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.simpleLanguage.R
import com.example.simpleLanguage.common.utils.ResourceUtils
import com.example.simpleLanguage.common.SGApplication
import com.example.simpleLanguage.common.Status
import com.example.simpleLanguage.common.di.SGConstants.RESET_INTERVAL
import com.example.simpleLanguage.common.di.SGConstants.START_INTERVAL
import com.example.simpleLanguage.common.di.SGConstants.UPDATE_INTERVAL
import com.example.simpleLanguage.common.utils.NetworkUtil.Companion.hasInternetConnection
import com.example.simpleLanguage.mainscreen.data.entity.Word
import com.example.simpleLanguage.mainscreen.di.DaggerMainScreenComponent
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModel
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainScreenViewModelFactory
    private lateinit var viewModel: MainScreenViewModel

    private val updateGameAnimationHandler = Handler()
    private lateinit var updateGameAnimationRunnable: Runnable


    private var translation = 50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainScreenComponent
            .builder()
            .appComponent((applicationContext as SGApplication).mAppComponent).build()
            .inject(this)


        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainScreenViewModel::class.java)

        prepareTheGame()
    }


    //getting the game environment ready
    private fun prepareTheGame() {
        updateScore()
        loadWords()
        viewModel.getWordsList()
        viewModel.observeScore()
        setUpGameAnimationRunnable()
    }


    //an observer to Words list request handling all statuses
    private fun loadWords() {
        viewModel.words.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> showLoading(true)

                Status.ERROR,
                Status.NETWORK_ERROR -> {
                    showLoading(false)
                    noContent()
                }

                Status.SUCCESS -> {
                    showLoading(false)
                    if (it != null) {
                        //LoadData and start the game
                        viewModel.wordsList = it.data as List<Word>
                        startGame()
                    } else {
                        //Show Error Message
                        noContent()
                    }
                }
            }
        })
    }


    // observer to score variable to update UI whenever changed.
    private fun updateScore() {
        viewModel.score.observe(this, Observer {
            score_counter.text = it.toString()
        })
    }


    override fun onResume() {
        super.onResume()
        //handling game to continue after being in background
        if (viewModel.isGameStarted)
            updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)
    }


    override fun onPause() {
        super.onPause()
        //handling game to pause after being going to background
        updateGameAnimationHandler.removeCallbacks(updateGameAnimationRunnable)
    }


    //the method that will be called when user click the right button
    fun rightClicked(view: View) {
        if (!isWordStillVisible() || moving_word_textView.visibility == View.GONE) {
            return
        }

        viewModel.isAnswered = true
        if (moving_word_textView.text == viewModel.wordsList[viewModel.wordIndex].text_spa) {
            calculateScore(true)
            notifyUserRightAnswer()
        } else {
            notifyUserWrongAnswer()
        }

        resetRound()

    }


    fun wrongClicked(view: View) {
        if (!isWordStillVisible() || moving_word_textView.visibility == View.GONE) {
            return
        }

        viewModel.isAnswered = true
        if (moving_word_textView.text != viewModel.wordsList[viewModel.wordIndex].text_spa) {
            calculateScore(true)
            notifyUserRightAnswer()
        } else {
            notifyUserWrongAnswer()
        }

        resetRound()

    }


    //intializing the GameAnimation Runnable
    private fun setUpGameAnimationRunnable() {
        updateGameAnimationRunnable = Runnable {
            run {
                //Update UI


                if (!isWordStillVisible() && !viewModel.isAnswered) {
                    resetRound()
                    return@run
                }
                moveWord()
                moving_word_textView.visibility = View.VISIBLE

                // Re-run it after the update interval
                updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, UPDATE_INTERVAL)
            }

        }
    }


    // a method to start the game
    private fun startGame() {
        viewModel.isGameStarted = true
        moving_word_textView.visibility = View.VISIBLE

        addWordsToTextView()

        updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)
    }


    // Animation of the moving word
    private fun moveWord() {
        translation += 50
        moving_word_textView.animate().translationY(translation.toFloat())
            .setDuration(RESET_INTERVAL)
    }


    // A method that returns whether the word ist off screen or not
    private fun isWordStillVisible(): Boolean {
        val screenBound =
            Rect(0, 0, ResourceUtils.getScreenwidth(), ResourceUtils.getScreenHeight())
        return moving_word_textView.getLocalVisibleRect(screenBound)
    }


    // A method that resets the round either if user answer or word gets off screen
    private fun resetRound() {
        translation = 50
        increaseIndex()
        viewModel.isAnswered = false
        updateGameAnimationHandler.removeCallbacks(updateGameAnimationRunnable)
        moving_word_textView.clearAnimation()
        moving_word_textView.visibility = View.GONE

        moving_word_textView.animate().y(-0f).setDuration(100)
        startNextRound()
    }


    // A method that starts next round
    private fun startNextRound() {
        updateGameAnimationHandler.postDelayed(updateGameAnimationRunnable, START_INTERVAL)
        addWordsToTextView()
    }

    // A method to increase words index and start over when reach end
    private fun increaseIndex() {
        if (viewModel.wordIndex == viewModel.wordsList.size) {
            viewModel.wordIndex = 0
        } else {
            viewModel.wordIndex++
        }
    }


    // updating words with the current index
    private fun addWordsToTextView() {
        //adding the static word in language 1 (English)
        static_word.text = viewModel.wordsList[viewModel.wordIndex].text_eng

        //checking if should add wrong or right word
        if (shouldAddTheRightWord()) {
            //if true add the right word
            moving_word_textView.text = viewModel.wordsList[viewModel.wordIndex].text_spa

        } else {
            //if false get random word from list
            moving_word_textView.text = getRandomWord()
        }
    }


    // A method that returns a random boolean deciding wether to add the right or the wrong word each time
    private fun shouldAddTheRightWord() = Random.nextBoolean()


    // A method that returns a random word from the words list
    private fun getRandomWord(): String {
        val randomIndex = Random.nextInt(viewModel.wordsList.size)
        return viewModel.wordsList[randomIndex].text_spa
    }


    //A method to reflect player answers on his score
    private fun calculateScore(isWin: Boolean) {
        if (isWin) {
            //increase score
            viewModel.updateScore()
        } else {
            //TODO: [FUTURE Work] can add deduction if wrong answer
        }
    }


    //a method to notify the user he answered right. For simplicity i used Toast
    private fun notifyUserRightAnswer() {
        val toast =
            Toast.makeText(this, ResourceUtils.getString(R.string.right_answer), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    //a method to notify the user he answered wrong. For simplicity i used Toast
    private fun notifyUserWrongAnswer() {
        val toast =
            Toast.makeText(this, ResourceUtils.getString(R.string.wrong_answer), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val view = toast.view
        toast.show()
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading_progressbar.visibility = View.VISIBLE
            no_content.visibility = View.GONE
        } else {
            loading_progressbar.visibility = View.GONE
        }
    }

    private fun noContent() {
        game_group.visibility = View.GONE
        no_content.visibility = View.VISIBLE
        hasInternetConnection().subscribe { hasInternet ->
            if (!hasInternet) no_content.text = ResourceUtils.getString(R.string.no_internet)
            else no_content.text = ResourceUtils.getString(R.string.error_message)
        }.isDisposed
    }

}
