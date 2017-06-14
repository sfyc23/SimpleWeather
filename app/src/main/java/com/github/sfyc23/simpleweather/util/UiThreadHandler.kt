package com.github.sfyc23.simpleweather.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock

/**
 * UI线程Handler
 * Author :leilei on 2017/6/14 12:03
 */
object UiThreadHandler {
    private val LOOP = 0x001
    private val LOOP_TIME = 0x002

    val uiHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                LOOP -> {
                    val runnable = msg.obj as Runnable
                    run(runnable)
                    loop(runnable, msg.arg1)
                }
                LOOP_TIME -> {
                    val handler = msg.obj as LoopHandler
                    try {
                        handler.run()
                    } catch (e: Exception) {
                    }

                    loop(handler, msg.arg1, --msg.arg2)
                }
            }
        }

        fun run(runnable: Runnable) {
            try {
                runnable.run()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private val token = Any()

    fun post(r: Runnable): Boolean {
        if (uiHandler == null) {
            return false
        }
        return uiHandler.post(ReleaseRunnable(r))
    }



    fun postOnce(r: Runnable): Boolean {
        if (uiHandler == null) {
            return false
        }
        uiHandler.removeCallbacks(r, token)
        return uiHandler.postAtTime(r, token, SystemClock.uptimeMillis())
    }

    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        if (uiHandler == null) {
            return false
        }
        return uiHandler.postDelayed(ReleaseRunnable(r), delayMillis)
    }


    fun postOnceDelayed(r: Runnable, delayMillis: Long): Boolean {
        if (uiHandler == null) {
            return false
        }
        uiHandler.removeCallbacks(r, token)
        return uiHandler.postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)
    }

    fun loop(runnable: Runnable, delay: Int) {
        if (uiHandler == null) {
            return
        }
        uiHandler.removeMessages(LOOP)
        val msg = Message.obtain()
        msg.what = LOOP
        msg.obj = runnable
        msg.arg1 = delay
        uiHandler.sendMessageDelayed(msg, delay.toLong())
    }

    fun stopLoop() {
        if (uiHandler == null) {
            return
        }
        uiHandler.removeMessages(LOOP)
    }

    fun loop(handler: LoopHandler?, delay: Int, time: Int) {
        if (uiHandler == null || handler == null) {
            return
        }
        uiHandler.removeMessages(LOOP_TIME)
        if (time == 0) {
            handler.end()
            return
        }
        val msg = Message.obtain()
        msg.what = LOOP_TIME
        msg.obj = handler
        msg.arg1 = delay
        msg.arg2 = time
        uiHandler.sendMessageDelayed(msg, delay.toLong())
    }

    interface LoopHandler {
        fun run()

        fun end()
    }

    /**
     * 可容错的Runnable

     * @author yangzc
     */
    class ReleaseRunnable(private val mRunnable: Runnable?) : Runnable {

        override fun run() {
            if (mRunnable != null) {
                try {
                    mRunnable.run()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

            }
        }

    }


}

