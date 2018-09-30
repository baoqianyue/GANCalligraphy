package com.barackbao.sketchpad.factory

import android.content.Context
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.controller.ControllerImpl

class BoardFactoryImpl : BoardFactory() {
    override fun getController(context: Context): Controller = ControllerImpl(context)
}