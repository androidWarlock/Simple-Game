package com.example.simpleLanguage.mainscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenRepository
import javax.inject.Inject

class MainScreenViewModelFactory @Inject constructor(var mainScreenRepository: MainScreenRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainScreenViewModel(mainScreenRepository) as T
    }

}
