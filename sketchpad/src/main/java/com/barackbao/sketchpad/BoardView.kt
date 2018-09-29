package com.barackbao.sketchpad

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.utils.CanvasUtil
import com.barackbao.sketchpad.utils.FormatUtil
import com.barackbao.sketchpad.utils.HistoryUtil
import com.barackbao.sketchpad.utils.Logger
import com.barackbao.sketchpad.view.layer.CacheLayer
import com.barackbao.sketchpad.view.layer.DrawCacheLayer
import com.barackbao.sketchpad.view.model.Model
import java.text.Format

/**
 * the surfaceview and the cache layer
 */
class BoardView(context: Context, val mPaint: Paint) : SurfaceView(context), SurfaceHolder.Callback {

    constructor(context: Context) : this(context, Paint())

    init {

    }

    /**
     * 当前view的格式
     */
    private var mFormat: Bitmap.Config? = null

    fun getFormat(): Bitmap.Config? = mFormat


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
    private lateinit var mHistory: HistoryUtil


    /**
     * 缓冲绘图层
     */
    private var mCacheLayer: CacheLayer? = null

    private var CLEAR_REDO_STACK: () -> Unit = {
        mHistory.clearRedoStack()
    }

    private var ADD_HISTORY: (Model) -> Unit = {
        mHistory.addHistory(it)
    }


    /**
     * 设置当前的扩展层对象
     * if it is null, means destory the extension layer
     */
    fun setCacheLayer(cacheLayer: CacheLayer?) {
        if (cacheLayer != null) {
            cacheLayer.prepare(CLEAR_REDO_STACK, ADD_HISTORY)
        } else if (mCacheLayer != null) {
            mCacheLayer?.onDestory()
        }
        mCacheLayer = cacheLayer
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        logger.e("surface changed, width = $width, height = $height")
        mFormat = FormatUtil.getConfig(format)
        cacheBitmap = Bitmap.createBitmap(width, height, mFormat)
        cacheCanvas = Canvas()
        cacheCanvas.setBitmap(cacheBitmap)
        mHistory = HistoryUtil(cacheCanvas)
        setCacheLayer(DrawCacheLayer(mFormat, width, height, mPaint))
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        logger.e("surface destroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        logger.e("surface created")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (command) {
            Controller.Command.DISABLE -> return true
            Controller.Command.ERASER -> {
                eraser(event)
            }
        }
    }


    /**
     * eraser path
     */
    private var mEraserPath = Path()
    private var mEraserX = 0f
    private var mEraserY = 0f
    private fun eraser(event: MotionEvent) {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mEraserPath.reset()
                mEraserPath.moveTo(x, y)
                mEraserX = x
                mEraserY = y
                CLEAR_REDO_STACK()
            }

            MotionEvent.ACTION_UP -> {
                ADD_HISTORY(HistoryUtil.toHistory(CanvasUtil.paintCopy(mPaint), CanvasUtil.pathCopy(mEraserPath)))
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(x - mEraserX) > 3 || Math.abs(y - mEraserY) > 3) {
                    mEraserPath.quadTo(mEraserX, mEraserY, (x + mEraserX) / 2, (y + mEraserY) / 2)
                    mEraserX = x
                    mEraserY = y
                    cacheCanvas.drawPath(mEraserPath, mPaint)
                }
            }
        }
    }


}