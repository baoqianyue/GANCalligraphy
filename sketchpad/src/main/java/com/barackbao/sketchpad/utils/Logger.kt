package com.barackbao.sketchpad.utils

import android.util.Log

/**
 * 日志输出类
 */

class Logger(private val tag: String) {

    constructor(clazz: Class<*>) : this(clazz.name)

    constructor(any: Any) : this(any.javaClass)

    fun e(any: Any?) {
        Log.e(tag, "$any")
    }

    fun d(any: Any?) {
        Log.e(tag, "$any")
    }

    fun i(any: Any?) {
        Log.e(tag, "$any")
    }

    fun v(any: Any?) {
        Log.e(tag, "$any")
    }

    fun w(any: Any?) {
        Log.e(tag, "$any")
    }


}