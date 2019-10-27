package com.example.simpleLanguage.common.utils

import com.example.simpleLanguage.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiUtils{

    val homeData: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BuildConfig.HOME_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()



    private fun getClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.interceptors().add(logging)
        }

        return builder.build()
    }
}