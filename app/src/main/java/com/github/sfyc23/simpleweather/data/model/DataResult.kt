package com.github.sfyc23.simpleweather.data.model

/**
 * Author :leilei on 2017/6/4 23:12
 */

/**
 * 当前天气总数据
 */
data class CurrentResult(
        var location: LocationEntity,
        var current: CurrentEntity
)


/**
 * 天气预报总数据。
 */
data class ForecastResult(
        var location: LocationEntity,
        var current: CurrentEntity,
        var forecast: ForecastEntity
)

/**
 * 历史数据
 */
data class HistoryWeatherResult(
        var location: LocationEntity,
        var forecast: ForecastEntity
)


///**
//* 搜索城市，检查是否可用
//*/
//data class SearchResult(
//
//)


/**
 * 地址信息
 */
data class LocationEntity(
        var name: String,
        var region: String,
        var country: String,
        var lat: Double ,
        var lon: Double ,
        var tz_id: String,
        var localtime_epoch:Int,
        var localtime: String
)


/**
 * 当前天气情况
 */
data class CurrentEntity(
        var last_updated:String,
        var temp_c: Double,
        var is_day: Int,
        var condition: ConditionEntity,
        var wind_kph: Double,
        var wind_degree: Int,
        var wind_dir: String,
        var pressure_mb: Double,
        var precip_mm: Double,
        var humidity: Int,
        var cloud: Int,
        var feelslike_c: Double,
        var vis_km: Double)

/**
 * 天气预报
 */
data class ForecastEntity(
        var forecastday: List<ForecastdayEntity>
)


data class ForecastdayEntity(
        var date: String,
        var day: DayEntity,
        var astro: AstroEntity,
        var hour: List<HourEntity>
)


data class DayEntity(
        var maxtemp_c: Double ,
        var mintemp_c: Double ,
        var avgtemp_c: Double ,
        var maxwind_kph: Double ,
        var totalprecip_mm:Double,
        var avgvis_km: Double ,
        var avghumidity: Double ,
        var condition: ConditionEntity
)


data class AstroEntity(
        var sunrise: String,
        var sunset: String,
        var moonrise: String,
        var moonset: String)


data class HourEntity(
        var time: String,
        var temp_c: Double,
        var is_day: Int,
        var condition: ConditionEntity,
        var wind_kph: Double,
        var wind_degree: Int,
        var wind_dir: String,
        var pressure_mb: Double,
        var precip_mm: Double,
        var humidity: Int,
        var cloud: Int,
        var feelslike_c: Double,
        var windchill_c: Double,
        var heatindex_c: Double,
        var dewpoint_c: Double,
        var will_it_rain: Int,
        var will_it_snow: Int,
        var vis_km: Double
)

/*
天气情况
 */
data class ConditionEntity(
        var text: String,
        var icon: String,
        var code: Int
)

data class SearchEntity(
        var id: Int,
        var name: String,
        var region: String,
        var country: String,
        var lat: Double,
        var lon: Double,
        var url: String)


data class ErrorResult(var error: ErrorEntity)

data class ErrorEntity(
        var code: Int,
        var message: String
)

