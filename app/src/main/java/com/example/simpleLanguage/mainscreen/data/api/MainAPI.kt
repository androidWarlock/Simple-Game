package com.example.simpleLanguage.mainscreen.data.api

import com.example.simpleLanguage.mainscreen.data.entity.Word
import retrofit2.Response
import retrofit2.http.GET

/**
 * This makes all the API calls for Main screen
 */
interface MainAPI {

    @GET
    suspend fun getWordsList(): Response<List<Word>?>
}