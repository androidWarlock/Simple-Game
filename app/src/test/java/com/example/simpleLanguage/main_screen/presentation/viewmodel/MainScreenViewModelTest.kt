package com.example.simpleLanguage.main_screen.presentation.viewmodel

import com.example.simpleLanguage.common.MockApplication
import com.example.simpleLanguage.common.TestUtils
import com.example.simpleLanguage.common.mock
import com.example.simpleLanguage.common.observeForTesting
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenRepository
import com.example.simpleLanguage.mainscreen.presentation.viewmodel.MainScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = MockApplication::class)
class MainScreenViewModelTest{
    private var repo: MainScreenRepository = mock<MainScreenRepository>()
    private lateinit var viewModel: MainScreenViewModel


    @Test
    fun getWordsSuccessTest() {

        runBlocking {

            val wordsList = TestUtils.getWordsList()
            Mockito.`when`(repo.getWordsList()).thenReturn(wordsList)

            viewModel = MainScreenViewModel(repo)

            viewModel.getWordsList()

            val liveData = viewModel.words
            liveData.observeForTesting {

                assert(wordsList == liveData.value!!.data)

            }
        }
    }


    @Test
    fun getWordsFailureTest() {

        runBlocking {

            Mockito.`when`(repo.getWordsList()).thenReturn(null)

            viewModel = MainScreenViewModel(repo)

            viewModel.getWordsList()

            val liveData = viewModel.words
            liveData.observeForTesting {

                assert(null == liveData.value!!.data)

            }
        }
    }

}