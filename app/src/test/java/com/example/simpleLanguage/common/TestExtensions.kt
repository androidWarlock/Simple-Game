package com.example.simpleLanguage.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


//extension for liveData to observe on it for testing purpose
fun <T> LiveData<T>.observeForTesting(codeBlock: () -> Unit) {


    val observer = Observer<T> {}
    try {

        observeForever(observer)
        codeBlock()

    } finally {
        removeObserver(observer)
    }

}