package com.example.simpleLanguage.common

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.example.simpleLanguage.common.Status.ERROR
import com.example.simpleLanguage.common.Status.LOADING
import com.example.simpleLanguage.common.Status.NETWORK_ERROR
import com.example.simpleLanguage.common.Status.SUCCESS

/**
 * A resource class to handle different statuses including error and empty response
 */
class Resource<T>(
        @param:NonNull @field:NonNull var status: Int,
        @param:Nullable @field:Nullable var data: T?,
        @param:Nullable @field:Nullable var message: String?) {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*>?

        if (status != resource!!.status) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = 7
        result = 31 * result + status
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\''.toString() +
                ", channelData=" + data +
                '}'.toString()
    }

    companion object {

        fun <T> success(@Nullable data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, @Nullable data: T): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> networkError(msg: String, @Nullable data: T): Resource<T> {
            return Resource(NETWORK_ERROR, data, msg)
        }

        fun <T> loading(@Nullable data: T): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}