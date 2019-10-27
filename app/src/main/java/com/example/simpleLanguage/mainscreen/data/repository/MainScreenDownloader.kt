package com.example.simpleLanguage.mainscreen.data.repository

import com.example.simpleLanguage.mainscreen.data.api.MainAPI
import com.example.simpleLanguage.mainscreen.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class MainScreenDownloader (private var mainApi: MainAPI): MainScreenRepository{

    override suspend fun getWordsList(): List<Word>? {
        return withContext(Dispatchers.IO){
            val response : Response<List<Word>?> = mainApi.getWordsList().execute()

            if (response != null && response.code() == 200) {
                return@withContext (response.body())
            }else{
                throw Exception()
            }
        }
    }


}