package com.barackbao.sketchpad.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceView
import com.barackbao.sketchpad.BoardView
import com.barackbao.sketchpad.shape.Shape
import com.barackbao.sketchpad.shape.ShapeWrapper
import com.barackbao.sketchpad.view.layer.BitmapCacheLayer
import com.barackbao.sketchpad.view.layer.DrawCacheLayer

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

    }

    override fun drawBitmap(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun undo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasUndo(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun redo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasRedo(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun width(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun height(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCommond(command: Controller.Command) {
        if (mView.command != command) {
            when(mView.command) {
                Controller.Command.DISABLE  -> {

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
            when(command) {
                Controller.Command.DISABLE -> {

                }
                Controller.Command.DARW -> {
                    val layer = DrawCacheLayer(mView.getFormat(), width(), height(),mPaint)
                    mView.setCacheLayer(layer)
                }
                Controller.Command.ERASER -> {
                    val layer =
                }
            }
        }
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toPicture(): Bitmap {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getView(): SurfaceView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}