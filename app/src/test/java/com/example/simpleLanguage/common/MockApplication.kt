package com.example.simpleLanguage.common

import com.example.simpleLanguage.common.di.DaggerAppComponent

//A mock class for Application class
class MockApplication : SGApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun initDaggerComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}
