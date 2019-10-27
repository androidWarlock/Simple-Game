package com.example.simpleLanguage.mainscreen.di

import com.example.simpleLanguage.common.ApiUtils
import com.example.simpleLanguage.common.di.ActivityScope
import com.example.simpleLanguage.mainscreen.data.api.MainAPI
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenDownloader
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenRepository
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule{
    @Provides
    @ActivityScope
    fun provideMainApi(): MainAPI {
        return ApiUtils.homeData.create(MainAPI::class.java)
    }

    @Provides
    @ActivityScope
    fun providesMainScreenRepository(mainScreenAPI: MainAPI): MainScreenRepository {
        return MainScreenDownloader(mainScreenAPI)
    }




}