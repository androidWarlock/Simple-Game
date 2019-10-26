package com.example.simpleLanguage.common

import android.content.Context
import com.example.simpleLanguage.common.di.AppComponent
import com.example.simpleLanguage.common.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class SGApplication : DaggerApplication() {

    lateinit var mAppComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = mAppComponent


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initDaggerComponent()
    }

    open fun initDaggerComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}