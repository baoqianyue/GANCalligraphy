package com.barackbao.sketchpad.shape

import android.graphics.Bitmap
import android.graphics.Paint
import android.view.MotionEvent
import com.barackbao.sketchpad.utils.CanvasUtil
import com.barackbao.sketchpad.utils.HistoryUtil
import com.barackbao.sketchpad.view.layer.CacheLayer

class ShapeCacheLayer(val shape: Shape,
                      format: Bitmap.Config?,
                      width: Int, height: Int,
                      paint: Paint) : CacheLayer(format, width, height, paint) {

    private lateinit var mPaint: Paint
    private var preX = 0f
    private var preY = 0f

    override fun onTouchEvent(event: MotionEvent) {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mPaint = CanvasUtil.paintCopy(paint)
                if (shape.isFull) {
                    mPaint.style = Paint.Style.FILL_AND_STROKE
                }
                preX = x
                preY = y
                mClearRedo()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                val path = CanvasUtil.pathCopy(shape.getShape(preX, preY, x, y))
                mAddHistory(HistoryUtil.toHistory(mPaint, path))
            }
            MotionEvent.ACTION_MOVE -> {
                val path = shape.getShape(preX, preY, x, y)
                clearCache()
                cacheCanvas.drawPath(path, mPaint)
            }
        }
    }


}