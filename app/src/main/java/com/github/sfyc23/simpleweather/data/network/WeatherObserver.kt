package com.github.sfyc23.simpleweather.data.network

import com.github.sfyc23.simpleweather.util.log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Author :leilei on 2017/6/7 10:44
 */
interface WeatherObserver<T> : Observer<T> {
    override fun onNext(t: T) {
        onWeatherNext(t)
    }

    override fun onError(e: Throwable?) {
        if (e === null) {
            return
        }
        val error = e.message
        if (error === null) {
            return
        }
        e.message?.log("error")

        var message: String? = null

        if (e is SocketTimeoutException) {
            message = "请求超时"
            onWeatherError(message)
            return
        }
        if (e is ConnectException) {
            message = "网络中断，请检查您的网络状态"
            onWeatherError(message)
            return
        }

        if (error.contains("401")) {
            message = "Api key 出错"
            onWeatherError(message)
            return
        }
        if (error.contains("400")) {
            message = "城市名称出错"
            onWeatherError(message)
            return
        }
        if (error.contains("403")) {
            message = "API key 次数已经超过"
            onWeatherError(message)
            return
        }
        onWeatherError(error)

    }

    open override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable?) {

    }

    fun onWeatherNext(t: T)
    fun onWeatherError(message: String)
}