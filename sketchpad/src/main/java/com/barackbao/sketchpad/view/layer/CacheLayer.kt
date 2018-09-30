package com.barackbao.sketchpad.view.layer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.barackbao.sketchpad.utils.CanvasUtil
import com.barackbao.sketchpad.utils.Logger
import com.barackbao.sketchpad.view.model.Model


/**
 * the cache layer interface, the layer be inited when surfaceview has size,and be destoryed
 * whe surface view be destoryed
 */
abstract class CacheLayer(val format: Bitmap.Config?,
                          val width: Int,
                          val height: Int,
                          val paint: Paint) {
    /**
     * the bitmap obj in the cache
     */
    val cacheBitmap = Bitmap.createBitmap(width, height, format)

    /**
     * the canvas obj
     */
    protected val cacheCanvas = Canvas().apply {
        setBitmap(cacheBitmap)
    }

    /**
     * the logger obj
     */
    protected val logger: Logger = Logger("Layer")

    /**
     * is drawing now
     */
    var isDrawing = false

    protected lateinit var mClearRedo: () -> Unit

    protected lateinit var mAddHistory: (Model) -> Unit

    fun prepare(clearRedo: () -> Unit,
                addHistoryUtil: (Model) -> Unit) {
        this.mClearRedo = clearRedo
        this.mAddHistory = mAddHistory
    }

    private var mDestoryListener: (() -> Unit)? = null

    fun setOnDestoryListener(destoryListener: () -> Unit) {
        this.mDestoryListener = destoryListener
    }

    /**
     *  called when the sufaceview destory
     */
    open fun onDestory() {
        logger.e("onDestory")
        cacheBitmap.recycle()
        mDestoryListener?.invoke()
    }

    fun clearCache() {
        CanvasUtil.clear(cacheCanvas)
    }

    abstract fun onTouchEvent(event: MotionEvent)


}