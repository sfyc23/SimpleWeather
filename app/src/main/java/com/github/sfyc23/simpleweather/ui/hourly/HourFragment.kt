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
import com.github.sfyc23.simpleweather.ui.daily.ForecastHourAdapter
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveStickyEvent
import com.github.sfyc23.simpleweather.util.rxbus.busToObservableSticky
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_daily.*


/**
 * Author :leilei on 2017/5/28 22:25
 */
class HourFragment : RxFragment() {

    private lateinit var  mAdapter : ForecastHourAdapter

    companion object Factory {
        fun newInstance(): HourFragment {
            return HourFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_hour, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAdapter = ForecastHourAdapter()

        recyclerView.apply {
            setHasFixedSize(true)
            var linearlayoutManager = LinearLayoutManager(context)
            layoutManager = linearlayoutManager
            adapter = mAdapter
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
        mAdapter.addData(fr.forecast.forecastday.get(0).hour)
    }

    override fun onDestroy() {
        super.onDestroy()
        busRemoveStickyEvent(UpdateForecastEvent::class.java)
    }
}