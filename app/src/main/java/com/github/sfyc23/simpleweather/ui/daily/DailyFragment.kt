package com.github.sfyc23.weather.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.model.ForecastResult
import com.github.sfyc23.simpleweather.ui.daily.ForecastDailyAdapter
import com.github.sfyc23.simpleweather.util.adapter.DividerDecoration
import com.github.sfyc23.simpleweather.util.adapter.StickyHeaderDecoration
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.fragment_daily.*


/**
 * Author :leilei on 2017/5/28 22:25
 */
class DailyFragment : Fragment() {

    companion object Factory {
        fun newInstance(): DailyFragment {
            return DailyFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_daily, container, false)

        return rootView
    }

    var spLastForecast: String by DelegatesExt.preference(OverviewFragment.SP_KEY_FORECAST, OverviewFragment.SP_VLAUE_DEFAULT_FORECAST)
    var gson: Gson = Gson()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val divider = DividerDecoration.Builder(this.activity)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.colorPrimary)
                .build()
        recyclerView.apply {
            setHasFixedSize(true)
            var linearlayoutManager = LinearLayoutManager(context)
            layoutManager = linearlayoutManager
//            addItemDecoration(divider)
        }

        try {
            var lfr = gson.fromJson(spLastForecast, ForecastResult::class.java)
            if (lfr.current != null) {
                loadData(lfr)
            }
        } catch (e: JsonSyntaxException) {
//        throw Throwable("格式化出错")
        }

    }

    fun loadData(fr: ForecastResult) {
        var adapter = ForecastDailyAdapter(fr.forecast.forecastday)

        val decor = StickyHeaderDecoration(adapter)
//        mRecyleView.setAdapter(mAdapter)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(decor, 0)

        val divider = DividerDecoration.Builder(this.activity)
                .setHeight(R.dimen.default_divider_height)
                .setColorResource(R.color.colorPrimary)
                .build()
        recyclerView.addItemDecoration(divider)
    }


}