package com.example.simpleLanguage.mainscreen.data.repository

import android.provider.UserDictionary
import com.example.simpleLanguage.mainscreen.data.api.MainAPI

class MainScreenDownloader (private var mainScreenAPI : MainAPI ): MainScreenRepository{
    override fun getWordsList(): List<UserDictionary.Words>? {


    }

}