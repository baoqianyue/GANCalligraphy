package com.barackbao.aicalligraphy.activity.genbook

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.activity.base.BaseActivity
import com.barackbao.aicalligraphy.model.CopyBook
import com.barackbao.aicalligraphy.model.WordOutline
import com.barackbao.aicalligraphy.network.RequestCenter
import com.barackbao.aicalligraphy.showToast
import com.barackbao.baselib.okhttp.listener.DisposeDataListener
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.factory.BoardFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.json.JSONArray

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
        controller.setCommond(Controller.Command.DARW)
        //设置画笔
//        changeWord(this)
        val simpleTraget: SimpleTarget<Drawable> = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                controller.setBackground(resource)
            }
        }
//        var url = changeWord()
        Glide.with(this).load(R.drawable.practicebgp).into(simpleTraget)
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
                    backgroundResource = R.drawable.practicebg
                }
                addView(view)
                padding = dip(50)
            }
            horizontalScrollView {
                lparams {
                    width = matchParent
                    height = dip(50)
                }
                linearLayout {
                    lparams(matchParent, matchParent)
                    verticalGravity = bottom
                    backgroundColor = GRAY2
                    orientation = LinearLayout.HORIZONTAL
                    textView(text = "换字", theme = R.style.ButtonStyle) {
                        onClick {
                            toast("换字")
                        }
                    }
                    textView(text = "画笔", theme = R.style.ButtonStyle) {
                        onClick {
                            controller.setCommond(Controller.Command.DARW)
                        }
                    }
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
                    textView(text = "恢复", theme = R.style.ButtonStyle) {
                        onClick {
                            controller.redo()
                        }
                    }
                    textView(text = "结构", theme = R.style.ButtonStyle) {
                        onClick {
                            controller.setCommond(Controller.Command.EXPLODE)
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
                }
            }
        }

    }


    private fun changeWord(context: Context) {


        RequestCenter.requestWordsOutline(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any?) {
                var jsonArray = listOf(responseObj.toString() as JSONArray)
                for (i in jsonArray.indices) {
                    var wordOutline = Gson().fromJson<WordOutline>(jsonArray[i].toString(), WordOutline::class.java)

                }
            }

            override fun onFailure(responseObj: Any?) {
                showToast("网络错误")
            }

        })

    }
}

