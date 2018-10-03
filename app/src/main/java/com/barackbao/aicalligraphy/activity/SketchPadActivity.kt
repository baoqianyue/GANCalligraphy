package com.barackbao.aicalligraphy.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.activity.base.BaseActivity
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.factory.BoardFactory
import org.jetbrains.anko.*

class SketchPadActivity : BaseActivity() {


    companion object {
        private val GRAY1 = 0xD1.gray.opaque
        private val GRAY2 = 0x96.gray.opaque
        private val GRAY3 = 0x66.gray.opaque
    }

    private lateinit var controller: Controller
    private lateinit var mPaintWindow: PopupWindow
    private lateinit var mEraserWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        val factory = BoardFactory.getFactory()
        controller = factory.getController(this)
        val view = controller.getView()
        controller.setBackgroundColor(Color.WHITE) //todo
        //设置画笔
        controller.setCommond(Controller.Command.DARW)
        controller.setStrokeWidth(40f)
        controller.setStrokeColor(Color.BLACK)
        linearLayout {
            lparams {
                width = matchParent
                height = matchParent
            }
            orientation = LinearLayout.VERTICAL
            linearLayout {
                lparams {
                    width = matchParent
                    height = 0
                    weight = 1f
                    backgroundResource = R.drawable.barack
                }
                addView(view)
                padding = dip(50)
            }
            linearLayout {
                lparams(matchParent, dip(60))
                verticalGravity = bottom
                backgroundColor = GRAY2
                orientation = LinearLayout.HORIZONTAL
                textView(text = "橡皮", theme = R.style.ButtonStyle) {
                    onClick {
                        controller.setCommond(Controller.Command.ERASER)
                    }
                }
                textView(text = "撤销", theme = R.style.ButtonStyle) {
                    onClick {
                        controller.undo()
                    }
                }
                textView(text = "重写", theme = R.style.ButtonStyle) {
                    onClick {
                        controller.redo()
                    }
                }
                switch(R.style.ButtonStyle) {
                    isChecked = false
                    onCheckedChange { _, switch ->
                        if (switch) {
                            toast("暂存状态")
                            controller.setCommond(Controller.Command.DISABLE)
                        } else {
                            toast("绘制状态")
                        }
                    }
                }
                textView(text = "保存", theme = R.style.ButtonStyle) {
                    //todo
                }
                textView(text = "清屏", theme = R.style.ButtonStyle) {
                    onClick {
                        alert(title = "提醒", message = "是否清空内容") {
                            yesButton {
                                controller.clear()
                            }
                            noButton { }
                        }.show()
                    }
                }
                textView(text = "爆炸", theme = R.style.ButtonStyle) {
                    onClick { controller.setCommond(Controller.Command.EXPLODE) }
                }
            }
        }


    }
}