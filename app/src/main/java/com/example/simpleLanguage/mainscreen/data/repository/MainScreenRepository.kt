package com.example.simpleLanguage.mainscreen.data.repository

import com.example.simpleLanguage.mainscreen.data.entity.Word

interface MainScreenRepository {
    suspend fun getWordsList(): List<Word>?
}