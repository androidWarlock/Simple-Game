package com.example.simpleLanguage.main_screen.data.repository

import com.example.simpleLanguage.common.CoroutinesTestRule
import com.example.simpleLanguage.common.MockApplication
import com.example.simpleLanguage.common.TestUtils
import com.example.simpleLanguage.mainscreen.data.api.MainAPI
import com.example.simpleLanguage.mainscreen.data.repository.MainScreenDownloader
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = MockApplication::class)
class MainScreenDownloaderTest{
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T


    private var mainScreenDownloader : MainScreenDownloader? = null
    private var mainAPI: MainAPI = mock(MainAPI::class.java,Mockito.RETURNS_DEEP_STUBS)

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup(){
        mainScreenDownloader = MainScreenDownloader(mainAPI)
    }


    @After
    fun tearDown() {
        mainScreenDownloader = null
    }

    @Test
    fun `given success response with data when getWordList() then validate response data`() {
        runBlocking {
            val words  = TestUtils.getWordsList()
            val response = Response.success(200, words)

            Mockito.`when`(mainAPI.getWordsList().execute()).thenReturn(response)

            Assert.assertEquals(mainScreenDownloader!!.getWordsList() ,words)


        }
    }

}