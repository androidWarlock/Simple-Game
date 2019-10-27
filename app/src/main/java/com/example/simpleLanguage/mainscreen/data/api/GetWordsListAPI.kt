package com.example.simpleLanguage.mainscreen.data.api

import com.example.simpleLanguage.common.ApiUtils
import com.example.simpleLanguage.common.BaseAPI
import com.example.simpleLanguage.mainscreen.data.entity.Word
import retrofit2.Response

abstract class GetWordsListAPI : BaseAPI() {

    companion object {

        fun getWords(): Response<List<Word>?> {
            val service = ApiUtils.homeData.create(MainAPI::class.java)
            val call = service.getWordsList()

            return call.execute()
        }

    }
}