package com.barackbao.sketchpad.utils

import android.graphics.*

object CanvasUtil {

    private val CLEAR_PAINT = Paint().apply {
        this.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun clear(canvas: Canvas) {
        canvas.drawPaint(CLEAR_PAINT)
    }


    /**
     * copy a paint
     */
    fun paintCopy(paint: Paint): Paint {
        val mPaint = Paint()
        mPaint.set(paint)
        return mPaint
    }

    fun pathCopy(path: Path): Path {
        val mPath = Path()
        mPath.set(path)
        return mPath
    }

}