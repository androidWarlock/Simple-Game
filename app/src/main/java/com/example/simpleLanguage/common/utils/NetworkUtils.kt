package com.example.simpleLanguage.common.utils


import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class NetworkUtil {
    companion object {
        //Connect to a Socket on the Internet Google DNS to check if there's internet or not. Very fast and reliable
        fun hasInternetConnection(): Single<Boolean> {
            return Single.fromCallable {
                try {
                    // Connect to Google DNS to check for connection
                    val timeoutMs = 1500
                    val socket = Socket()
                    val socketAddress = InetSocketAddress("8.8.8.8", 53)

                    socket.connect(socketAddress, timeoutMs)
                    socket.close()

                    true
                } catch (e: IOException) {
                    false
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}
