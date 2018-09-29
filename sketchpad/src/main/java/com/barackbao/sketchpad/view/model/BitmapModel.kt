package com.barackbao.sketchpad.view.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF

class BitmapModel(private val bitmap: Bitmap,
                  private val bounds: RectF) : Model() {

    private val mBounds = RectF()
    private val mMatrix = Matrix()

    override fun drawHistory(canvas: Canvas, history: History, isBounds: Boolean): Boolean {
        mBounds.set(bounds)
        mMatrix.reset()
        if (history.isMove) {
            mMatrix.setTranslate(history.x, history.y)
            mBounds.move(history.x, history.y)
        }
        canvas.save()
        canvas.matrix = mMatrix
        canvas.drawBitmap(bitmap, null, bounds, null)
        canvas.restore()
        if (isBounds) {
            frameBounds = RectF(mBounds)
            frameBounds.addWidth(5f)
        }

        return isBounds
    }


}