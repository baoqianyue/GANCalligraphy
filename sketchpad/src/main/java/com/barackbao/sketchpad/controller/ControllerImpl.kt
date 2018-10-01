package com.barackbao.sketchpad.controller

import android.content.Context
import android.graphics.*
import android.view.SurfaceView
import com.barackbao.sketchpad.BoardView
import com.barackbao.sketchpad.shape.Shape
import com.barackbao.sketchpad.shape.ShapeCacheLayer
import com.barackbao.sketchpad.shape.ShapeWrapper
import com.barackbao.sketchpad.view.layer.BitmapCacheLayer
import com.barackbao.sketchpad.view.layer.DrawCacheLayer
import com.barackbao.sketchpad.view.layer.ExplodeCacheLayer

class ControllerImpl(context: Context) : Controller {

    /**
     * the shape wrapper obj
     */
    private val mShape = ShapeWrapper()

    /**
     * the container of bitmap
     */
    private val mBitmap = BitmapCacheLayer.BitmapContainer()

    /**
     * the global paint obj
     */
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).let {
        it.color = Color.BLACK
        it.style = Paint.Style.STROKE
        it.strokeWidth = 1F
        it.strokeJoin = Paint.Join.ROUND
        it.strokeCap = Paint.Cap.ROUND
        it
    }


    /**
     * the board view obj
     */
    private val mView = BoardView(context, mPaint)


    override fun setStrokeWidth(width: Float) {
        mPaint.strokeWidth = width
    }

    override fun setStrokeColor(color: Int) {
        mPaint.color = color
    }

    override fun setBackgroundColor(color: Int) {
        mView.setBackgroundColor(color)
    }

    override fun drawShape(shape: Shape) {
        mShape.shape = shape
    }

    override fun drawBitmap(bitmap: Bitmap) {
        mBitmap.bitmap = bitmap
    }

    override fun undo() {
        if (hasUndo())
            mView.undo()
    }

    override fun hasUndo(): Boolean {
        return mView.hasUndo()
    }

    override fun redo() {
        if (hasRedo())
            mView.redo()
    }

    override fun hasRedo(): Boolean {
        return mView.hasRedo()
    }

    override fun width(): Int {
        return mView.width
    }

    override fun height(): Int {
        return mView.height
    }

    override fun setCommond(command: Controller.Command) {
        if (mView.command != command) {
            when (mView.command) {
                Controller.Command.DISABLE -> {

                }
                Controller.Command.DARW -> {
                    mView.setCacheLayer(null)
                }
                Controller.Command.ERASER -> {
                    mPaint.xfermode = null
                }
                Controller.Command.SHAPE -> {
                    mView.setCacheLayer(null)
                }
                Controller.Command.EXPLODE -> {
                    mView.setCacheLayer(null)
                    mView.invalidate()
                }
                Controller.Command.BITMAP -> {
                    mView.setCacheLayer(null)
                }
            }
            when (command) {
                Controller.Command.DISABLE -> {

                }
                Controller.Command.DARW -> {
                    val layer = DrawCacheLayer(mView.getFormat(), width(), height(), mPaint)
                    mView.setCacheLayer(layer)
                }
                Controller.Command.ERASER -> {
                    mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                }
                Controller.Command.SHAPE -> {
                    val layer = ShapeCacheLayer(mShape, mView.getFormat(), width(), height(), mPaint)
                    mView.setCacheLayer(layer)
                }
                Controller.Command.EXPLODE -> {
                    val layer = ExplodeCacheLayer(mView.getFormat(), width(), height())
                    mView.setCacheLayer(layer)
                    mView.explode()
                }
                Controller.Command.BITMAP -> {
                    val layer = BitmapCacheLayer(mBitmap, mView.getFormat(), width(), height())
                    mView.setCacheLayer(layer)
                }
            }
        }
        mView.command = command
    }

    override fun clear() {
        mView.clear()
    }

    override fun toPicture(): Bitmap {
        return mView.toPicture()
    }

    override fun getView(): SurfaceView {
        return mView
    }
}