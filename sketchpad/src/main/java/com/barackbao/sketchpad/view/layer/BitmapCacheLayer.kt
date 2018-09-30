package com.barackbao.sketchpad.view.layer

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import com.barackbao.sketchpad.utils.HistoryUtil

class BitmapCacheLayer(private val mBitmap: BitmapContainer,
                       format: Bitmap.Config?,
                       width: Int, height: Int) : CacheLayer(format, width, height, Paint()) {

    private var startX = 0f
    private var startY = 0f
    private var bounds = RectF()
    override fun onTouchEvent(event: MotionEvent) {
        val x = event.x
        val y = event.y
        clearCache()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                startX = x
                startY = y
                mClearRedo()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mAddHistory(HistoryUtil.toHistory(mBitmap.bitmap!!, RectF(bounds)))
            }
            MotionEvent.ACTION_MOVE -> {
                bounds.top = if (startY < y) startY else y
                bounds.bottom = if (startY >= y) startY else y
                bounds.left = if (startX < x) startX else x
                bounds.right = if (startX >= x) startX else x
                cacheCanvas.drawBitmap(mBitmap.bitmap, null, bounds, paint)
            }
        }
    }


    class BitmapContainer {
        var bitmap: Bitmap? = null
    }
}