package com.github.sfyc23.simpleweather.data.event

import com.github.sfyc23.simpleweather.data.model.ForecastResult

/**
 * Author :leilei on 2017/6/8 23:58
 */
data class CityEvent(var cityName:String)

data class UpdateForecastEvent(var fr: ForecastResult)