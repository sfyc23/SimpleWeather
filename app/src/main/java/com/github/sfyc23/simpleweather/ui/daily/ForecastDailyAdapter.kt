package com.github.sfyc23.simpleweather.ui.daily

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.model.ForecastdayEntity
import com.github.sfyc23.simpleweather.util.adapter.StickyHeaderAdapter
import com.github.sfyc23.weather.extensions.inflate
import com.github.sfyc23.weather.extensions.loadImg
import com.github.sfyc23.weather.extensions.to24Hour
import kotlinx.android.synthetic.main.item_forecast_daily.view.*
import kotlinx.android.synthetic.main.item_forecast_daily_header.view.*

/**
 * Author :leilei on 2017/6/12 11:19
 */
class ForecastDailyAdapter(var datas: List<ForecastdayEntity> = ArrayList<ForecastdayEntity>())
    : RecyclerView.Adapter<ForecastDailyAdapter.ViewHolder>(), StickyHeaderAdapter<ForecastDailyAdapter.HeaderViewHolder> {

    override fun getHeaderId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(parent.inflate(R.layout.item_forecast_daily_header))
    }

    override fun onBindHeaderViewHolder(headerViewHolder: HeaderViewHolder, position: Int) {
        headerViewHolder.bindData(datas.get(position).date)
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
//            itemView.tvWeek.text = fm.date.toDayOfWeek();

            itemView.ivCondition.loadImg(fm.day.condition.icon)
            itemView.tvCondition.text = fm.day.condition.text

            var temp = "${fm.day.maxtemp_c}°/ ${fm.day.mintemp_c}°"

            itemView.tvTemp.text = temp

            with(fm.astro){

                itemView.tvMoonrise.text = moonrise.to24Hour()
                itemView.tvMoonset.text = moonset.to24Hour()
                itemView.tvSunrise.text = sunrise.to24Hour()
                itemView.tvSunset.text = sunset.to24Hour()
            }

            itemView.tvTotalprecip.text = fm.day.totalprecip_mm.toString()
            itemView.tvWind.text = fm.day.maxwind_kph.toString()

        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(date:String) {
            itemView.tvDailyTime.text = date
        }
    }


}
