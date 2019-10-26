package com.example.simpleLanguage.common.di

import android.app.Application
import com.example.simpleLanguage.common.SGApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by tejachirala on 17/11/17.
 */

@Module
open class AppModule {

    @Singleton
    @Provides
    open fun provideApp(application: Application) : SGApplication = application as SGApplication
}
