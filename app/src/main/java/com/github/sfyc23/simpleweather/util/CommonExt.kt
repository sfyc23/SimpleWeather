package com.github.sfyc23.weather.extensions

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.sfyc23.simpleweather.R
import com.github.stuxuhai.jpinyin.PinyinException
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import org.jetbrains.anko.getStackTraceString
import org.joda.time.format.DateTimeFormat
import java.text.DateFormat
import java.util.*


/**
 * Author :leilei on 2017/5/26 17:03
 */
/**
 * Long 的扩展对象
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun Double.toKph() = "${this}km/h"
fun Double.toTempByCelsius() = "${this}°"
fun Double.toKm() = "${this}km"

fun Int.toPercent() = "${this}%"
//fun Double.percent() = "${this}%"

/*
    根据日期得到周几
 */
fun String.toDayOfWeek(dateFormat: String = "yyyy-MM-dd"): String {
    val dtf = DateTimeFormat.forPattern(dateFormat)
    try {
        val dateTime = dtf.parseDateTime(this)
        return dateTime.dayOfWeek().getAsShortText(Locale.CHINA)
    } catch (e: Exception) {
        return dtf.parseDateTime("2010-10-10").dayOfWeek().getAsShortText(Locale.CHINA)
    }
}

/**
 * 将数据是 (am,pm)格式的hour，修改成 24小时的
 */
fun String.to24Hour(dateFormat: String = "hh:mm a"): String {
    val oldDtf = DateTimeFormat.forPattern(dateFormat).withLocale(Locale.ENGLISH)
    val newDtf = DateTimeFormat.forPattern("HH:mm")
//    this.log("to24Hour")
    try {
        val dateTime = oldDtf.parseDateTime(this)
        return newDtf.print(dateTime)
    } catch (e: Exception) {
        e.getStackTraceString()
        return this
    }
}

/**
 * 将数据是 (am,pm)格式的hour，修改成 24小时的
 */
fun String.toWeekAndMonthDay(dateFormat: String = "yyyy-mm-dd"): String {
    val oldDtf = DateTimeFormat.forPattern(dateFormat)
    val newDtf = DateTimeFormat.forPattern("E dd/mm").withLocale(Locale.CHINA)
//    this.log("to24Hour")
    try {
        val dateTime = oldDtf.parseDateTime(this)
        return newDtf.print(dateTime)
    } catch (e: Exception) {
        e.getStackTraceString()
        return this
    }
}

/**
 * 将16方位修改成对应中文
 */
fun String.toWindDir():String{
    var windDir:String="东南西北风"
    when(this){
        "N"-> windDir="北"
        "NNE"-> windDir="北东北"
        "NE"-> windDir="东北"
        "ENE"-> windDir="东东北"

        "E"-> windDir="东"
        "ESE"-> windDir="东东南"
        "SE"-> windDir="东南"
        "SSE"-> windDir="南东南"

        "S"-> windDir="南"
        "SSW"-> windDir="南西南"
        "SW"-> windDir="西南"
        "WSW"-> windDir="西西南"

        "W"-> windDir="西"
        "WNW"-> windDir="西西北"
        "NW"-> windDir="西北"
        "NNW"-> windDir="北西北"
    }

    return windDir
}





fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Glide.with(context).load(R.drawable.ic_launcher).into(this)
    } else {
        if (imageUrl.contains("https") || imageUrl.contains("http")) {
            Glide.with(context).load(imageUrl).into(this)
        } else {
            Glide.with(context).load("https:" + imageUrl).into(this)
        }
    }
}

val View.ctx: Context
    get() = context



fun String.toPinyin(): String {
    try {
        val py = PinyinHelper.convertToPinyinString(this, "", PinyinFormat.WITHOUT_TONE) // nǐ,hǎo,shì,jiè
        return py
    } catch (pe: PinyinException) {
        return this
    }
}

