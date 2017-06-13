package com.github.sfyc23.simpleweather.util.rxbus


import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.Observable

/**
 * Author :leilei on 2017/6/9 02:03
 */


//--------------------------------发送------------------------------------------


fun Context.busPostSticky(event: Any) {
    RxBus.getDefault().postSticky(event)
}

fun Fragment.busPostSticky(event: Any) {
    RxBus.getDefault().postSticky(event)
}

//fun Activity.busPostSticky(event: Any) {
//    RxBus.getDefault().postSticky(event)
//}


//fun Any.busPostSticky(event: Any) {
//    RxBus.getDefault().postSticky(event)
//}

//--------------------------------订阅------------------------------------------
//fun <T> Any.busToObservableSticky(eventType: Class<T>) : Observable<T> {
//    return RxBus.getDefault().toObservableSticky(eventType)
//}

fun <T> Context.busToObservableSticky(eventType: Class<T>) : Observable<T> {
    return RxBus.getDefault().toObservableSticky(eventType)
}

//fun <T> Activity.busToObservableSticky(eventType: Class<T>) : Observable<T> {
//    return RxBus.getDefault().toObservableSticky(eventType)
//}
//
fun <T> Fragment.busToObservableSticky(eventType: Class<T>) : Observable<T> {
    return RxBus.getDefault().toObservableSticky(eventType)
}


//--------------------------------移除单个------------------------------------------

fun <T> Context.busRemoveStickyEvent(eventType: Class<T>) : T {
    return RxBus.getDefault().removeStickyEvent(eventType)
}

//fun <T> Activity.busRemoveStickyEvent(eventType: Class<T>) : T {
//    return RxBus.getDefault().removeStickyEvent(eventType)
//}
//
fun <T> Fragment.busRemoveStickyEvent(eventType: Class<T>) : T {
    return RxBus.getDefault().removeStickyEvent(eventType)
}

//----------------------移除所有的Sticky事件--------------------------
fun Context.busRemoveAllStickyEvents() {
    RxBus.getDefault().removeAllStickyEvents()
}

//fun Activity.busRemoveAllStickyEvents() {
//    RxBus.getDefault().removeAllStickyEvents()
//}
//
fun Fragment.busRemoveAllStickyEvents() {
    RxBus.getDefault().removeAllStickyEvents()
}




