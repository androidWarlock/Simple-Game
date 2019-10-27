package com.example.simpleLanguage.common

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.simpleLanguage.common.di.AppComponent
import com.example.simpleLanguage.common.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class SGApplication : DaggerApplication(), LifecycleObserver {

    private lateinit var mAppComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = mAppComponent


    override fun onCreate() {
        super.onCreate()

        // To make Vector drawable work on pre L
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }
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