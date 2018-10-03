package com.barackbao.sketchpad.controller

import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.graphics.drawable.Drawable
import android.view.SurfaceView
import com.barackbao.sketchpad.shape.Shape


/**
 * 画板控制器借口
 */

interface Controller {
    enum class Command {

        /**
         *  停止触摸，但是用户可以操控控制面板
         */
        DISABLE,
        /**
         *  开启触摸，事件直接落在底层缓冲区
         */
        DARW,
        /**
         *  图形绘制，事件落在图形缓冲区
         *  会先检测绘图层是否存在，不存在讲创建该层并插入图形对象
         */
        SHAPE,
        /**
         *  图片绘制，图片缓冲区创建，拦截事件
         *  会先检测图片层是否存在，不存在会创建
         */
        BITMAP,
        /**
         *  擦除事件
         */
        ERASER,
        /**
         * 爆炸模式
         */
        EXPLODE;
    }

    /**
     *  设置画笔粗细
     */
    fun setStrokeWidth(width: Float)

    fun setStrokeColor(color: Int)

    fun setBackgroundColor(color: Int)

    fun setBackground(res: Drawable)

    fun drawShape(shape: Shape)

    fun drawBitmap(bitmap: Bitmap)

    fun undo()
    /**
     * 是否能够继续撤销
     */
    fun hasUndo(): Boolean

    fun redo()

    /**
     * 当前是否能够继续重做
     */
    fun hasRedo(): Boolean

    fun width(): Int

    fun height(): Int

    fun setCommond(command: Command)

    fun clear()

    fun toPicture(): Bitmap

    fun getView(): SurfaceView


}