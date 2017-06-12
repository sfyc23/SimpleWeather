package com.github.sfyc23.simpleweather.data.api

import com.github.sfyc23.simpleweather.data.model.CurrentResult
import com.github.sfyc23.simpleweather.data.model.ForecastResult
import com.github.sfyc23.simpleweather.data.model.HistoryWeatherResult
import com.github.sfyc23.simpleweather.data.model.SearchEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author :leilei on 2017/6/7 07:42
 */
interface WeatherService{

    companion object {
//        val BASE_URL = "https://api.apixu.com/v1/"
            val BASE_URL = "https://api.apixu.com/v1/"
    }

    /**
     搜索城市
    https://api.apixu.com/v1/search.json?key=bd6cf545b42d43cb96184023172905&lang=zh&q=beijing


    //历史天气
    https://api.apixu.com/v1/history.json?key=bd6cf545b42d43cb96184023172905&lang=zh&q=beijing&dt=2017-06-02


    //天气预报
    https://api.apixu.com/v1/forecast.json?key=bd6cf545b42d43cb96184023172905&lang=zh&q=beijing&days=1


    //实时天气
    https://api.apixu.com/v1/current.json?key=bd6cf545b42d43cb96184023172905&lang=zh&q=beijing
     */


    @GET("current.json")
    abstract fun getWeatherCurrent(@Query("q") cityName: String): Observable<CurrentResult>

    @GET("forecast.json")
    abstract fun getWeatherForeacast(@Query("q") cityName: String, @Query("days") day: Int = 7): Observable<ForecastResult>

    @GET("history.json")
    abstract fun getWeatherHistory(@Query("q") cityName: String, @Query("dt") dt: String): Observable<HistoryWeatherResult>

    @GET("search.json")
    abstract fun getWeatherSearch(@Query("q") cityName: String): Observable<List<SearchEntity>>

}