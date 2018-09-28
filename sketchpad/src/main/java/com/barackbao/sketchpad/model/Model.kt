package com.barackbao.sketchpad.model

import android.graphics.*
import java.util.*

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



}