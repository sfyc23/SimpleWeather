package com.github.sfyc23.simpleweather.data.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Author :leilei on 2017/4/29 13:38
 */
class ResponseConvertFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) throw NullPointerException("gson == null")
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?,
                                       retrofit: Retrofit?): Converter<ResponseBody, out Any?> {
//        var ll = GsonResponseBodyConverter<Any?>(gson!!,type!!)
        return GsonResponseBodyConverter<Any?>(gson!!, type!!)
    }

    companion object {

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        @JvmOverloads
        fun create(gson: Gson = Gson()): ResponseConvertFactory {
            return ResponseConvertFactory(gson)
        }
    }
}
/**
 * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
 */
