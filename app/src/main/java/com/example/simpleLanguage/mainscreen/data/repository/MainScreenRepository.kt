package com.example.simpleLanguage.mainscreen.data.repository

import com.example.simpleLanguage.mainscreen.data.entity.Word

/**
 * This is the contract for repository.
 */
interface MainScreenRepository {
    suspend fun getWordsList(): List<Word>?
}