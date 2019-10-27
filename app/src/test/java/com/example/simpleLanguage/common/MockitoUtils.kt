package com.example.simpleLanguage.common

import org.mockito.Mockito
//a helper functions to make code more readable

inline fun <reified T> mock() = Mockito.mock(T::class.java)
