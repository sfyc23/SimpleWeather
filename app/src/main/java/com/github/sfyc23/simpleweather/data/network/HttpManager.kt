package com.github.sfyc23.simpleweather.data.network

import com.github.sfyc23.simpleweather.data.api.WeatherService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Author :leilei on 2017/6/7 07:37
 */
class HttpManager private constructor(){
    companion object{
        fun getInstance() = Holder.instance
    }
    private object Holder{
        val instance = HttpManager()
    }

    private val DEFAULT_TIMEOUT = 10L
    val mRetrofit: Retrofit
    val weatherService: WeatherService

    init {
        var okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor (DefaultInterceptor())
                .build();

        mRetrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(WeatherService.BASE_URL)
                .client(okHttpClient)
                .build()

        mRetrofit.baseUrl()
        weatherService = mRetrofit.create(WeatherService::class.java)
    }

}


class DefaultInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
//                bd6cf545b42d43cb96184023172905
                .addQueryParameter("key", "bd6cf545b42d43cb96184023172905")
//                .addQueryParameter("lang","zh_cn")//语言类型
                .addQueryParameter("lang","zh")//语言类型
                .build()
        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}