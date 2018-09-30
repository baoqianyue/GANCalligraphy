package com.barackbao.sketchpad.factory

import android.content.Context
import com.barackbao.sketchpad.controller.Controller

abstract class BoardFactory {
    companion object {
        fun getFactory(): BoardFactory = BoardFactoryImpl()
    }

    /**
     * get the board controller and it is single, with the demise of the drawing board
     */
    abstract fun getController(context: Context): Controller
}