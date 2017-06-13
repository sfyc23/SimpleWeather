package com.github.sfyc23.weather.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.event.UpdateForecastEvent
import com.github.sfyc23.simpleweather.data.model.ForecastResult
import com.github.sfyc23.simpleweather.ui.daily.ForecastDailyAdapter
import com.github.sfyc23.simpleweather.util.adapter.DividerDecoration
import com.github.sfyc23.simpleweather.util.adapter.StickyHeaderDecoration
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveStickyEvent
import com.github.sfyc23.simpleweather.util.rxbus.busToObservableSticky
import com.google.gson.Gson
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_daily.*


/**
 * Author :leilei on 2017/5/28 22:25
 */
class DailyFragment : RxFragment() {

    var spLastForecast: String by DelegatesExt.preference(OverviewFragment.SP_KEY_FORECAST, OverviewFragment.SP_VLAUE_DEFAULT_FORECAST)
    var gson: Gson = Gson()
    var mAdapter  = ForecastDailyAdapter()

    companion object Factory {
        fun newInstance(): DailyFragment {
            return DailyFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_daily, container, false)

        return rootView
    }




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
            val decor = StickyHeaderDecoration(mAdapter)
//        mRecyleView.setAdapter(mAdapter)
            adapter = mAdapter
            addItemDecoration(decor, 0)
        }


        loadData(DelegatesExt.forecastResult)

        busToObservableSticky(UpdateForecastEvent::class.java)
                .compose(this.bindToLifecycle())
                .subscribe {
            loadData(it.fr)
        }

    }

    fun loadData(fr: ForecastResult?) {
        if (fr == null) {
            return
        }
        mAdapter.addData(fr.forecast.forecastday)


    }

    override fun onDestroy() {
        super.onDestroy()
        busRemoveStickyEvent(UpdateForecastEvent::class.java)
    }

}