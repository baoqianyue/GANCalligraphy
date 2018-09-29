package com.barackbao.sketchpad.view.model

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path


class PathModel(val paint: Paint, val path: Path) : Model() {

    private val mPath = Path()
    private val mMatrix = Matrix()

    override fun drawHistory(canvas: Canvas, history: History, isBounds: Boolean): Boolean {
        mPath.reset()
        mMatrix.reset()
        mPath.addPath(path)
        if (history.isMove) {
            mPath.offset(history.x, history.y)
        }
        if (history.isScale) {
            mMatrix.setScale(history.scale, history.scale, history.centerX, history.centerY)
        }
        if (history.isRotate) {
            mMatrix.postRotate(history.rotate, history.centerX, history.centerY)
        }

        mPath.transform(mMatrix)
        canvas.drawPath(mPath, paint)
        if (isBounds) {
            mPath.computeBounds(frameBounds, true)
            frameBounds.addWidth(paint.strokeWidth)
        }
        return isBounds
    }

}