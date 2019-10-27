package com.example.simpleLanguage.mainscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleLanguage.common.Resource
import com.example.simpleLanguage.mainscreen.data.entity.Word
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * This is the viewModel for MainScreen
 */
class MainScreenViewModel(var repo: MainScreenRepository): ViewModel(){

    private var scorecounter = 0
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    private val _words = MutableLiveData<Resource<List<Word>>>()
    val words: LiveData<Resource<List<Word>>>
        get() = _words

    private val wordsExceptionHandler = CoroutineExceptionHandler { _, exception ->
        sendWordsErrorMessage(exception)
    }


     fun getWordsList(){
        viewModelScope.launch (Dispatchers.Main + wordsExceptionHandler) {
            val words = repo.getWordsList()
            if (!words.isNullOrEmpty()){
                _words.value = Resource.success(words)
            }else{
                sendWordsErrorMessage(Throwable())
            }
        }
    }

    fun observeScore(){
        viewModelScope.launch (Dispatchers.Main ) {
            _score.value = scorecounter
        }
    }

    fun updateScore(){
        scorecounter++
        _score.value = scorecounter
    }


    fun resetScore(){
        scorecounter = 0
    }





    private fun sendWordsErrorMessage(exception: Throwable) {
        if (exception is IOException) {
            _words.value = Resource.networkError("", null) as Resource<List<Word>>
        } else {
            _words.value = Resource.error("", null) as Resource<List<Word>>
        }
    }


}

