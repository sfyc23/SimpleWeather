package com.github.sfyc23.weather.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.event.CityEvent
import com.github.sfyc23.simpleweather.data.model.ForecastResult
import com.github.sfyc23.simpleweather.data.network.HttpManager
import com.github.sfyc23.simpleweather.data.network.WeatherObserver
import com.github.sfyc23.simpleweather.ui.city.CityActivity
import com.github.sfyc23.simpleweather.util.log
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveStickyEvent
import com.github.sfyc23.simpleweather.util.rxbus.busToObservableSticky
import com.github.sfyc23.weather.extensions.inflate
import com.github.sfyc23.weather.extensions.loadImg
import com.github.sfyc23.weather.extensions.toTempString
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_overview.*
import org.jetbrains.anko.support.v4.toast
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.util.*




/**
 * Author :leilei on 2017/5/28 22:25
 */
class OverviewFragment : Fragment() {

    companion object Factory {
        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }

        val SP_KEY_UPDATE_TIME = "update_time"
        val SP_VLAUE_DEFAULT_UPDATE_TIME = -1L

        val SP_KEY_FORECAST = "last_forecast"
        val SP_VLAUE_DEFAULT_FORECAST = "{}"

        //最小刷新间隔15分钟
        var MIN_REFRESH_INTERVAL: Long = 15 * 60 * 1000
    }


    var spCityName: String by DelegatesExt.preference(CityActivity.SP_KEY_CITY_NAME, CityActivity.SP_VLAUE_DEFAULT_NAME)
    var spUpdateTime: Long by DelegatesExt.preference(SP_KEY_UPDATE_TIME, SP_VLAUE_DEFAULT_UPDATE_TIME)
    var spLastForecast: String by DelegatesExt.preference(SP_KEY_FORECAST, SP_VLAUE_DEFAULT_FORECAST)
    var gson: Gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = container?.inflate(R.layout.fragment_overview)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        var spCityName :String by DelegatesExt.preference(activity, CityActivity.CITY_NAME, CityActivity.DEFAULT_NAME)
//        tvCity.text = spCityName

        requestWeather(spCityName);

//        srlOverview.setOnClickListener {  }

        busToObservableSticky(CityEvent::class.java).subscribe {
            requestWeather(it.cityName, true)
        }

        srlOverview.setOnRefreshListener {
            requestWeather(spCityName)
        }

        spUpdateTime.log()

        try {
            var lfr = gson.fromJson(spLastForecast, ForecastResult::class.java)
            if (lfr.current != null) {
                loadData(lfr)
            }
        } catch (e: JsonSyntaxException) {
//        throw Throwable("格式化出错")
        }
        activity.runOnUiThread {
            //输出格式为｛15：52 星期一 12/06｝
            val dateFormat = DateTimeFormat.forPattern("HH:mm E dd/MM a")
                    .withLocale(Locale.CHINA)

            tvCurrentTime.text = dateFormat.print(LocalDateTime())

        }

    }


    private fun requestWeather(cityName: String, isForced: Boolean = false) {

        //是否跳过15分钟内刷新一次的判断
        if (!isForced) {
            var currentTime: Long = Date().time
            if (currentTime - spUpdateTime < MIN_REFRESH_INTERVAL) {
                return;
            }
        }

        var weatherService = HttpManager.getInstance().weatherService
        weatherService.getWeatherForeacast(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    srlOverview.isRefreshing = true
                }
                .doFinally {
                    srlOverview.isRefreshing = false
                }
                .subscribe(object : WeatherObserver<ForecastResult> {
                    override fun onWeatherError(message: String) {
                        toast(message)
                    }

                    override fun onWeatherNext(fr: ForecastResult) {

                        spUpdateTime = Date().time
                        spLastForecast = gson.toJson(fr)

                        DelegatesExt.forecastResult = fr

                        loadData(fr)

                    }
                })

    }

    fun loadData(fr: ForecastResult) {

        tvLastUpdateTime.text = "最近更新:刚刚"
//        var temp = "${fm.day.maxtemp_c}°/ ${fm.day.mintemp_c}°"

        tvMaxTemp.text = "${fr.forecast.forecastday.get(0).day.maxtemp_c}°"
        tvMinTemp.text = "${fr.forecast.forecastday.get(0).day.mintemp_c}°"
        with(fr.current) {

            tvCondition.text = condition.text
            ivCondition.loadImg("https:" + condition.icon)
            tvTemp.text = temp_c.toTempString()

            tvFeelLike.text = feelslike_c.toTempString()
            tvCloud.text = cloud.toString() + "%"
            tvWind.text = wind_kph.toString() + " kph " + wind_degree + "°"
            tvHumidty.text = humidity.toString() + "%"
            tvVisibility.text = vis_km.toString() + " km"
            tvPressure.text = pressure_mb.toString() + " mbar"

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        busRemoveStickyEvent(CityEvent::class.java)
    }


}

