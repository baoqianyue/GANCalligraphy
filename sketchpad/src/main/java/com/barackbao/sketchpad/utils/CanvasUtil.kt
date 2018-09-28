package com.barackbao.sketchpad.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode

object CanvasUtil {

    private val CLEAR_PAINT = Paint().apply {
        this.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun clear(canvas: Canvas){
        canvas.drawPaint(CLEAR_PAINT)
    }


}