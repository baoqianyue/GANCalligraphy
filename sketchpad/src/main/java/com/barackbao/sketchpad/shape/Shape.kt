package com.barackbao.sketchpad.shape

import android.graphics.Path

abstract class Shape {

    /**
     * is fit the inner area
     */
    open var isFull = false

    /**
     * return the path and the related parameters of target shape
     */
    abstract fun getShape(x: Float, y: Float, endX: Float, endY: Float): Path
}