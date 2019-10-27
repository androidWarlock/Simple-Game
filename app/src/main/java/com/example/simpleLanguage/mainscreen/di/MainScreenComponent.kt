package com.example.simpleLanguage.mainscreen.di

import com.example.simpleLanguage.common.di.ActivityScope
import com.example.simpleLanguage.common.di.AppComponent
import com.example.simpleLanguage.mainscreen.presentation.ui.MainActivity
import dagger.Component

@ActivityScope
@Component (dependencies = [AppComponent::class], modules = [MainScreenModule::class])
interface MainScreenComponent{

    fun inject(mainActivity: MainActivity)

}