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


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Glide.with(context).load(R.mipmap.ic_launcher).into(this)
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


fun Double.toTempString(): String {
    val df = this.toString() + "°"
    return df.format(this)
}

fun String.toPinyin(): String {
    try {
        val py = PinyinHelper.convertToPinyinString(this, "", PinyinFormat.WITHOUT_TONE) // nǐ,hǎo,shì,jiè
        return py
    } catch (pe: PinyinException) {
        return this
    }
}

