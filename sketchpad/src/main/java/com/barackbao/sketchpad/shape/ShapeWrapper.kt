package com.barackbao.sketchpad.shape

import android.graphics.Path

class ShapeWrapper : Shape() {


    val shape: Shape? = null

    override var isFull: Boolean
        get() = super.isFull
        set(value) {
            shape!!.isFull = value
        }


    override fun getShape(x: Float, y: Float, endX: Float, endY: Float): Path = shape!!.getShape(x, y, endX, endY)

}