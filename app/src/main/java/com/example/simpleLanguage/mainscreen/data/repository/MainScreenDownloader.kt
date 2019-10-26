package com.example.simpleLanguage.mainscreen.data.repository

import com.example.simpleLanguage.mainscreen.data.api.MainAPI
import com.example.simpleLanguage.mainscreen.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainScreenDownloader (private var mainScreenAPI : MainAPI ): MainScreenRepository{

    override suspend fun getWordsList(): List<Word>? {
        return withContext(Dispatchers.IO){
            val response : Response<List<Word>?> = mainScreenAPI.getWordsList()
            if (response != null && response.code() == 200) {
                val list : List<Word>? = (response.body())
                return@withContext list
            }else{
                throw Exception()
            }
        }
    }


}