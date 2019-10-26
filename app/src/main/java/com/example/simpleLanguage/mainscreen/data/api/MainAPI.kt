package com.example.simpleLanguage.mainscreen.data.api

import retrofit2.http.GET

/**
 * This makes all the API calls for Main screen
 */
interface MainAPI {

    @GET
    fun getWordsList()
}