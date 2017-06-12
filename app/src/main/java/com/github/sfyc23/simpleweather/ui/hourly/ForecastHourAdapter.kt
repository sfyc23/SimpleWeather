package com.github.sfyc23.simpleweather.ui.daily

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.model.HourEntity
import com.github.sfyc23.weather.extensions.inflate
import com.github.sfyc23.weather.extensions.loadImg
import kotlinx.android.synthetic.main.item_forecast_hour.view.*

/**
 * Author :leilei on 2017/6/12 11:19
 */
class ForecastHourAdapter(var datas: List<HourEntity> = ArrayList()) : RecyclerView.Adapter<ForecastHourAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(datas.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_forecast_hour))
    }

    override fun getItemCount() = datas.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(he: HourEntity) {

            itemView.tvTime.text = he.time.substring(10)

            itemView.ivCondition.loadImg(he.condition.icon)
            itemView.tvTemp.text = he.temp_c.toString()+"°"

            itemView.tvWind.text = he.wind_kph.toString()+"km/h"


//            itemView.tvMaxTemp.text = fm.day.maxtemp_c.toTempString()
//            itemView.tvMinTemp.text = fm.day.mintemp_c.toTempString()
        }
    }
}