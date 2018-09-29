package com.barackbao.sketchpad.utils

import android.graphics.Bitmap

object FormatUtil {

    private val imgConfigs = arrayOf(null,
            Bitmap.Config.ALPHA_8,
            null,
            Bitmap.Config.RGB_565,
            Bitmap.Config.ARGB_4444,
            Bitmap.Config.ARGB_8888)


    fun getConfig(index: Int): Bitmap.Config? = imgConfigs[index]
}