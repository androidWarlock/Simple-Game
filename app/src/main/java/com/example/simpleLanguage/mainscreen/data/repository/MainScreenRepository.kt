package com.example.simpleLanguage.mainscreen.data.repository

import android.provider.UserDictionary

interface MainScreenRepository {
    fun getWordsList(): List<UserDictionary.Words>?
}