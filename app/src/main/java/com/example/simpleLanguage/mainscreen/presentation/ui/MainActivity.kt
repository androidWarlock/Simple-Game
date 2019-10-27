package com.example.simpleLanguage.mainscreen.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.simpleLanguage.R
import com.example.simpleLanguage.common.SGApplication
import com.example.simpleLanguage.mainscreen.di.DaggerMainScreenComponent
import com.example.simpleLanguage.mainscreen.di.MainScreenComponent
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModel
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainScreenViewModelFactory
    private lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainScreenComponent
            .builder()
            .appComponent((applicationContext as SGApplication).mAppComponent).build()
            .inject(this)


        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainScreenViewModel::class.java)

        viewModel.getWordsList()
    }

}
