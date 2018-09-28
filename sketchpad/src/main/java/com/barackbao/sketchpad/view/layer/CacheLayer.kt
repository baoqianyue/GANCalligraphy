package com.barackbao.sketchpad.view.layer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.barackbao.sketchpad.utils.Logger


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


}