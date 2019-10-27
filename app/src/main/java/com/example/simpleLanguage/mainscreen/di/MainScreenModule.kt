package com.example.simpleLanguage.mainscreen.di

import com.example.simpleLanguage.common.utils.ApiUtils
import com.example.simpleLanguage.common.di.ActivityScope
import com.example.simpleLanguage.mainscreen.data.api.MainAPI
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenDownloader
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenRepository
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModelFactory
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
    fun providesMainScreenRepository(mainAPI: MainAPI): MainScreenRepository {
        return MainScreenDownloader(mainAPI)
    }

    @Provides
    @ActivityScope
    fun providesMainScreenViewModelFactory(mainScreenRepository: MainScreenRepository): MainScreenViewModelFactory {
        return MainScreenViewModelFactory(mainScreenRepository)
    }


}