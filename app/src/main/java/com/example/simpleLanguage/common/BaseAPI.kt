package com.example.simpleLanguage.common

abstract class BaseAPI {

    /**
     * this method is being called when response body is null
     * @param code the response status
     */
    abstract fun onEmptyResponse(code: Int)

    abstract fun onError(t: Throwable)

}