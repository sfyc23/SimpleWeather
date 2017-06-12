package com.github.sfyc23.simpleweather.ui

import android.app.Application
import com.antonioleiva.weatherapp.extensions.DelegatesExt

/**
 * Author :leilei on 2017/6/8 11:48
 */
class MyApplication : Application() {

    //companion object 伴⽣对象定义 扩展函数和属性：
    companion object {
        var instance: MyApplication by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}