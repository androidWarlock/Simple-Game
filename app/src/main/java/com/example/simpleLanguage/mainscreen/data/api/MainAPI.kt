package com.example.simpleLanguage.mainscreen.data.api

import com.example.simpleLanguage.mainscreen.data.entity.Word
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * This makes all the API calls for Main screen
 */
interface MainAPI {

    @GET("./")
    fun getWordsList(): Call<List<Word>?>
}