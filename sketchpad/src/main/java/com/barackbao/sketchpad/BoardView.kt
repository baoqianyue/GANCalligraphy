package com.barackbao.sketchpad

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.utils.HistoryUtil
import com.barackbao.sketchpad.utils.Logger
import com.barackbao.sketchpad.view.layer.CacheLayer

/**
 * the surfaceview and the cache layer
 */
class BoardView(context :Context, val mPaint: Paint): SurfaceView(context),SurfaceHolder.Callback {

    constructor(context:Context) :this(context, Paint())

    init {

    }

    /**
     * 当前view的格式
     */
    private var mFormat:Bitmap.Config? = null

    fun getFormat():Bitmap.Config? = mFormat


    /**
     * 设置绘制命令
     */
    var command = Controller.Command.DARW

    private val logger = Logger(this)

    /**
     * 最底层的缓冲区及绘制对象
     */
    private lateinit var cacheBitmap: Bitmap
    private lateinit var cacheCanvas: Canvas
    private lateinit var mHistory:HistoryUtil


    /**
     * 缓冲绘图层
     */
    private val mCacheLayer:CacheLayer? = null



    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}