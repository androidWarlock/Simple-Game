package com.example.simpleLanguage.common

import androidx.annotation.IntDef

/**
 * This is for network statuses
 */
object Status {

    const val SUCCESS = 100
    const val ERROR = 101
    const val NETWORK_ERROR = 102
    const val LOADING = 103


    @IntDef(SUCCESS, ERROR, NETWORK_ERROR, LOADING)
    @Retention
    annotation class State

}