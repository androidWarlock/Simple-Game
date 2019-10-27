package com.example.simpleLanguage.common

import com.example.simpleLanguage.mainscreen.data.entity.Word
//creating dummy Data objects for testing
object TestUtils {
    fun getWordsList(): List<Word>{
        val wordsList = ArrayList<Word>()
        wordsList.add(Word("",""))
        return wordsList
    }
}