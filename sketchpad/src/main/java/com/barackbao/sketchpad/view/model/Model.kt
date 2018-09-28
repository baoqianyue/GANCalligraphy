package com.barackbao.sketchpad.view.model

import android.graphics.*
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

abstract class Model {


    companion object {
        var explode = false
    }

    private val mFramePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 1F
        strokeJoin = Paint.Join.ROUND //线连接处圆角
        strokeCap = Paint.Cap.ROUND //线帽
        pathEffect = DashPathEffect(floatArrayOf(1f, 1f, 3f, 3f), 0f) //虚线尺寸
    }

    private val mFramePath = Path()
    protected var frameBounds: RectF = RectF() //画帧边界

    /**
     * 变换历史
     */
    protected data class History(var centerX: Float = 0f,
                                 var centerY: Float = 0f,
                                 var isRotate: Boolean = false,
                                 var rotate: Float = 0f,
                                 var isMove: Boolean = false,
                                 var x: Float = 0f,
                                 var y: Float = 0f,
                                 var isScale: Boolean = false,
                                 var scale: Float = 1f)


    /**
     * 记录变换历史
     */
    private var mHistory = History()

    private var startX = 0f
    private var startY = 0f

    protected val mUndoStack = Stack<History>().apply {
        push(mHistory.copy())
    }

    protected val mRedoStack = Stack<History>()


    /**
     * 扩展边框
     */
    protected fun RectF.addWidth(width: Float) {
        this.top -= width
        this.left -= width
        this.bottom += width
        this.right += width
    }

    /**
     * 移动边框
     */
    protected fun RectF.move(absX: Float, absY: Float) {
        this.top += absY
        this.left += absX
        this.bottom += absY
        this.right += absX
    }

    /**
     * 缩放边框
     */
    protected fun RectF.scale(scale: Float, centerX: Float = 0f, centerY: Float = 0f) {
        top = (top - centerY) * scale + centerY
        left = (left - centerX) * scale + centerX
        bottom = (bottom - centerY) * scale + centerY
        right = (right - centerX) * scale + centerX
    }

    /**
     * 旋转边框
     */
    protected fun RectF.rotate(rotate: Float, centerX: Float = 0f, centerY: Float = 0f) {
        val sinRotate = sin(rotate.toDouble().toFloat())
        val cosRotate = cos(rotate.toDouble().toFloat())
        val newHeight = height() * sinRotate + width() * cosRotate
        val newWidth = height() * sinRotate + width() * sinRotate
        right = left + newWidth
        bottom = top + newHeight
        val x2 = (centerX - left) * cosRotate + (centerY - bottom) * sinRotate + left
        val y2 = (centerY - bottom) * cosRotate - (centerX - left) * sinRotate + bottom
        move(centerX - x2, centerY - y2)
    }

    /**
     * 判断当前点是否落在自身RectF区域内
     */
    private fun RectF.inner(x: Float, y: Float): Boolean {
        return x > left && x < right && y > top && y < bottom
    }

    fun dispatcher(x: Float, y: Float): Boolean = frameBounds.inner(x, y)

    fun hasUndo(): Boolean = mUndoStack.size > 1
    fun hasRedo(): Boolean = mRedoStack.size != 0

    fun undo() {
        val history = mUndoStack.pop() //撤销
        mRedoStack.push(history) //撤销操作压入恢复
        mHistory = mUndoStack.peek().copy() //当前
    }

    fun redo() {
        val history = mRedoStack.pop()
        mUndoStack.push(history)
        mHistory = history.copy()
    }

    /**
     * 通知Model保存当前状态
     */
    fun save() {
        mUndoStack.push(mHistory.copy())
        mRedoStack.clear()
    }

    /**
     * 返回值
     */
    protected abstract fun drawHistory(canvas: Canvas, history: History, isBounds: Boolean): Boolean

    /**
     * 绘制当前Model边界
     */
    protected fun drawFrame(canvas: Canvas, bounds: RectF) {
        mFramePath.reset()
        mFramePath.quadTo(bounds.left, bounds.top, bounds.right, bounds.top)
        mFramePath.quadTo(bounds.right, bounds.top, bounds.right, bounds.bottom)
        mFramePath.quadTo(bounds.right, bounds.bottom, bounds.left, bounds.bottom)
        mFramePath.quadTo(bounds.left, bounds.bottom, bounds.left, bounds.top)
        canvas.drawPath(mFramePath, mFramePaint)
    }

    /**
     * 绘制model
     */
    fun draw(canvas: Canvas) {
        if (explode) {
            val isChange = drawHistory(canvas, mHistory, true)
            if (isChange) {
                drawFrame(canvas, frameBounds)
            }
        } else {
            drawHistory(canvas, mHistory, false)
        }
    }

    /**
     * init the start x,
     */
    fun startTo(x: Float, y: Float) {
        startX = x
        startY = y
    }

    /**
     * move the current model
     */
    fun moveTo(x: Float, y: Float) {
        mHistory.centerX = x
        mHistory.centerY = y
        mHistory.isMove = true
        mHistory.x = x - startX
        mHistory.y = y - startY
        val top = mUndoStack.peek()
        mHistory.x += top.x
        mHistory.y += top.y
    }


    /**
     * rotate current model, parameter is angle, have to call before the draw() method
     */
    fun rotate(angle: Float) {
        mHistory.isRotate = true
        mHistory.rotate = angle + mUndoStack.peek().rotate
    }


    /**
     * scale current model
     */
    private val MIN_SCALE = 0.5f
    private val MAX_SCALE = 2F

    fun scale(scale: Float) {
        var mScale = scale
        mHistory.isScale = true
        mScale *= mUndoStack.peek().scale
        if (mScale < MIN_SCALE) {
            mHistory.scale = MIN_SCALE
        } else if (mScale > MAX_SCALE)
            mHistory.scale = MAX_SCALE
        else
            mHistory.scale = mScale
    }

}