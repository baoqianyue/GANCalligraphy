package com.barackbao.sketchpad.view.layer

import android.graphics.Bitmap
import android.graphics.Paint
import android.view.MotionEvent

class DrawCacheLayer(format: Bitmap.Config?,
                     width: Int, height: Int,
                     paint: Paint) : CacheLayer(format,width,height, paint){


    init {
        isDrawing  = true
    }
    override fun onTouchEvent(event: MotionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}