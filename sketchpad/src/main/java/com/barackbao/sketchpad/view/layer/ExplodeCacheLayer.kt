package com.barackbao.sketchpad.view.layer

import android.graphics.Bitmap
import android.graphics.Paint
import android.view.Display
import android.view.MotionEvent
import com.barackbao.sketchpad.utils.HistoryUtil
import com.barackbao.sketchpad.view.model.Model
import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt

class ExplodeCacheLayer(format: Bitmap.Config?,
                        width: Int,
                        height: Int) : CacheLayer(format, width, height, Paint()) {


    private lateinit var mHistory: HistoryUtil


    fun onCreate(history: HistoryUtil) {
        this.mHistory = history
        history.mRedoStack.clear()
        Model.explode = true
        mHistory.redrawCanvas()
    }

    override fun onDestory() {
        Model.explode = false
        mHistory.redrawCanvas()
        super.onDestory()
    }

    private var mModel: Model? = null

    private var p1x = 0f
    private var p1y = 0f
    private var p2x = 0f
    private var p2y = 0f
    private var preLength = 0f
    private var preAngle = 0f

    override fun onTouchEvent(event: MotionEvent) {
        clearCache()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                p1x = event.x
                p1y = event.y
                mModel = getTop(p1x, p1y)
                mHistory.redrawCanvas()
                mModel?.startTo(p1x, p1y)
                mModel?.draw(cacheCanvas)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val id = event.getPointerId(index)
                if (id == 0) {
                    p1x = event.getX(id)
                    p1y = event.getY(id)
                } else if (id == 1) {
                    p2x = event.getX(id)
                    p2y = event.getY(id)
                }
                preLength = sqrt((p2y - p1y) * (p2y - p1y) + (p2x - p1x) * (p2x - p1x).toDouble()).toFloat()
                preAngle = 90 * atan2((p2y - p1y).toDouble(), (p2x - p1x).toDouble()).toFloat()
                mModel?.startTo((p1x + p2x) / 2, (p1y + p2y) / 2)
                mModel?.draw(cacheCanvas)
            }
            MotionEvent.ACTION_MOVE -> {
                val count = event.pointerCount
                if (count == 1) {
                    val index = event.actionIndex
                    val id = event.getPointerId(index)
                    if (id == 0) {
                        p1x = event.x
                        p1y = event.y
                        mModel?.moveTo(p1x, p1y)
                    } else if (id == 1) {
                        p2x = event.x
                        p2y = event.y
                        mModel?.moveTo(p2x, p2y)
                    }
                } else {
                    val id1 = event.getPointerId(0)
                    val id2 = event.getPointerId(1)
                    if (id1 > 1 && id2 > 1) {

                    } else if (id1 <= 1 && id2 <= 1) {
                        val tp1x = event.getX(id1)
                        val tp1y = event.getY(id1)
                        val tp2x = event.getX(id2)
                        val tp2y = event.getY(id2)
                        mModel?.moveTo((tp1x + tp2x) / 2, (tp1y + tp2y) / 2)
                        val curLength = sqrt((tp2y - tp1y) * (tp2y - tp1y) + (tp2x - tp1x) * (tp2x - tp1x).toDouble()).toFloat()
                        val scale = curLength / preLength
                        mModel?.scale(scale)
                        val angle = 90 * atan2((tp2y - tp1y).toDouble(), (tp2x - tp1x).toDouble()).toFloat()
                        mModel?.rotate(angle - preAngle)
                    } else if (id1 == 0) {
                        p1x = event.getX(id1)
                        p1y = event.getY(id1)
                        mModel?.moveTo(p1x, p1y)
                    } else if (id1 == 1) {
                        p2x = event.getX(id1)
                        p2y = event.getY(id1)
                        mModel?.moveTo(p2x, p2y)
                    } else if (id2 == 0) {
                        p1x = event.getX(id2)
                        p1y = event.getY(id2)
                        mModel?.moveTo(p1x, p1y)
                    } else if (id2 == 1) {
                        p2x = event.getX(id2)
                        p2y = event.getY(id2)
                        mModel?.moveTo(p2x, p2y)
                    }
                }
                mModel?.draw(cacheCanvas)
            }
            MotionEvent.ACTION_POINTER_UP -> {

            }
            MotionEvent.ACTION_UP -> {
                mModel?.save()
                if (mModel != null) {
                    mHistory.mUndoStack.push(mModel)
                    mHistory.redrawCanvas()
                }
                isDrawing = false
            }
        }
    }

    private fun getTop(x: Float, y: Float): Model? {
        val tempStack = Stack<Model>()
        var model: Model? = null
        while (mHistory.mUndoStack.isNotEmpty()) {
            val it = mHistory.mUndoStack.pop()
            if (it.dispatcher(x, y)) {
                model = it
                break
            }
            tempStack.push(it)
        }

        while (tempStack.isNotEmpty()) {
            mHistory.mUndoStack.push(tempStack.pop())
        }
        return model
    }


}