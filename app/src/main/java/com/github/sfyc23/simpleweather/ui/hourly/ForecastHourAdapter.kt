package com.github.sfyc23.simpleweather.ui.daily

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.model.HourEntity
import com.github.sfyc23.weather.extensions.*
import kotlinx.android.synthetic.main.item_forecast_hour.view.*

/**
 * Author :leilei on 2017/6/12 11:19
 */
class ForecastHourAdapter(var datas: List<HourEntity> = emptyList<HourEntity>()) : RecyclerView.Adapter<ForecastHourAdapter.ViewHolder>() {

    fun addData(newDatas: List<HourEntity>){
        datas = newDatas
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(datas.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_forecast_hour))
    }

    override fun getItemCount() = datas.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(he: HourEntity) {

            //日期格式为2017-06-05 00:00，只需要显示 10位之后的 00：00
            itemView.tvTime.text = he.time.substring(10)

            itemView.ivCondition.loadImg(he.condition.icon)
            itemView.tvTemp.text = he.temp_c.toTempByCelsius()

            itemView.tvWind.text = he.wind_dir.toWindDir() + he.wind_kph.toKph()

        }
    }
}