package com.github.sfyc23.simpleweather.util.rxbus


import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.Observable
import org.jetbrains.anko.AnkoContext

/**
 * Author :leilei on 2017/6/9 02:03
 */

//--------------------------------发送------------------------------------------
inline fun Context.busPostSticky(event: Any) {
    RxBus.getDefault().postSticky(event)
}

inline fun AnkoContext<*>.busPostSticky(event: Any) {
    RxBus.getDefault().postSticky(event)
}

inline fun Fragment.busPostSticky(event: Any) {
    RxBus.getDefault().postSticky(event)
}

//--------------------------------订阅------------------------------------------
inline fun <T> Context.busToObservableSticky(eventType: Class<T>) : Observable<T> {
    return RxBus.getDefault().toObservableSticky(eventType)
}

inline fun <T> AnkoContext<*>.busToObservableSticky(eventType: Class<T>) : Observable<T> {
    return RxBus.getDefault().toObservableSticky(eventType)
}

inline fun <T> Fragment.busToObservableSticky(eventType: Class<T>) : Observable<T> {
    return RxBus.getDefault().toObservableSticky(eventType)
}


//--------------------------------移除单个------------------------------------------

inline fun <T> Context.busRemoveStickyEvent(eventType: Class<T>) : T {
    return RxBus.getDefault().removeStickyEvent(eventType)
}

inline fun <T> AnkoContext<*>.busRemoveStickyEvent(eventType: Class<T>) : T {
    return RxBus.getDefault().removeStickyEvent(eventType)
}

inline fun <T> Fragment.busRemoveStickyEvent(eventType: Class<T>) : T {
    return RxBus.getDefault().removeStickyEvent(eventType)
}

//----------------------移除所有的Sticky事件--------------------------
inline fun Context.busRemoveAllStickyEvents() {
    RxBus.getDefault().removeAllStickyEvents()
}

inline fun AnkoContext<*>.busRemoveAllStickyEvents() {
    RxBus.getDefault().removeAllStickyEvents()
}
inline fun Fragment.busRemoveAllStickyEvents() {
    RxBus.getDefault().removeAllStickyEvents()
}




