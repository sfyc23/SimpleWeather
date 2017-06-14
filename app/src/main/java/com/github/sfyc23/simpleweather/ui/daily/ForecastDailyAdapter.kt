package com.github.sfyc23.simpleweather.ui.daily

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.model.ForecastdayEntity
import com.github.sfyc23.simpleweather.util.adapter.StickyHeaderAdapter
import com.github.sfyc23.weather.extensions.*
import kotlinx.android.synthetic.main.item_forecast_daily.view.*
import kotlinx.android.synthetic.main.item_forecast_daily_header.view.*

/**
 * Author :leilei on 2017/6/12 11:19
 */
class ForecastDailyAdapter(var datas: List<ForecastdayEntity> =  emptyList<ForecastdayEntity>())
    : RecyclerView.Adapter<ForecastDailyAdapter.ViewHolder>(), StickyHeaderAdapter<ForecastDailyAdapter.HeaderViewHolder> {

    fun addData(newDatas: List<ForecastdayEntity>){
        datas = newDatas
        notifyDataSetChanged()
    }

    override fun getHeaderId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(parent.inflate(R.layout.item_forecast_daily_header))
    }

    override fun onBindHeaderViewHolder(headerViewHolder: HeaderViewHolder, position: Int) {
        var date = datas.get(position).date.toWeekAndMonthDay()
        if (position == 0) {
            date = date + " 今天"
        }
        headerViewHolder.bindData(date)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(datas.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_forecast_daily))
    }

    override fun getItemCount() = datas.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(fm: ForecastdayEntity) {
            with(fm.astro){

                itemView.tvMoonrise.text = moonrise.to24Hour()
                itemView.tvMoonset.text = moonset.to24Hour()
                itemView.tvSunrise.text = sunrise.to24Hour()
                itemView.tvSunset.text = sunset.to24Hour()
            }

            with(fm.day){
                itemView.ivCondition.loadImg(condition.icon)
                itemView.tvCondition.text = condition.text

                itemView.tvTemp.text = "${maxtemp_c}°/ ${mintemp_c}°"

                itemView.tvTotalprecip.text = totalprecip_mm.toString()
                itemView.tvWind.text = maxwind_kph.toKph()

            }


        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(date:String) {
            itemView.tvDailyTime.text = date
        }
    }


}
