package com.barackbao.aicalligraphy.activity.genbook

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
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
import com.barackbao.aicalligraphy.util.DisplayUtil
import com.barackbao.baselib.okhttp.listener.DisposeDataListener
import com.barackbao.sketchpad.controller.Controller
import com.barackbao.sketchpad.factory.BoardFactory
import com.barackbao.sketchpad.utils.Logger
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.jetbrains.anko.*
import org.json.JSONArray
import java.util.*

class SketchPadActivity : BaseActivity() {

    val wordsList by lazy {
        ArrayList<WordOutline>()
    }


    companion object {
        private val GRAY1 = 0xD1.gray.opaque
        private val GRAY2 = 0x96.gray.opaque
        private val GRAY3 = 0x66.gray.opaque
    }

    private lateinit var controller: Controller
    private lateinit var mPaintWindow: PopupWindow
    private lateinit var mEraserWindow: PopupWindow


//    val hander: Handler = object : Handler() {
//        override fun handleMessage(msg: Message?) {
//            if (msg!!.what == 0x11) {
//                var url = msg.obj
//                val simpleTraget: SimpleTarget<Drawable> = object : SimpleTarget<Drawable>() {
//                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                        controller.setBackground(resource)
//                    }
//                }
//                Glide.with(this@SketchPadActivity).load(url).into(simpleTraget)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        requestWordData()
        val factory = BoardFactory.getFactory()
        controller = factory.getController(this)
        val view = controller.getView()
        controller.setCommond(Controller.Command.DARW)
        //设置画笔
//        Thread(Runnable {
//            var url = changeWord().wordUrl
//            var msg = Message.obtain()
//            msg.what = 0x11
//            msg.obj = url
//            hander.sendMessage(msg)
//        })

        controller.setStrokeWidth(50f)
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
                    backgroundResource = R.drawable.sketchbg
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
                            controller.clear()
                            val simpleTraget: SimpleTarget<Drawable> = object : SimpleTarget<Drawable>() {
                                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                    controller.setBackground(resource)
                                }
                            }
                            var url = changeWord().wordUrl
                            Glide.with(this).load(url).into(simpleTraget)
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
                        onClick {
                            Thread.sleep(1000)
                            showToast("已保存到手机")
                        }

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

    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
    }

    private fun changeWord(): WordOutline {
        return wordsList[Random().nextInt(0 until wordsList.size - 1)]
    }


    private fun requestWordData() {
        RequestCenter.requestWordsOutline(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any) {
                wordsList.clear()
                var jsonArray: JSONArray = responseObj as JSONArray
                for (i in 0..(jsonArray.length() - 1)) {
                    var wordOutline: WordOutline = Gson().fromJson<WordOutline>(jsonArray.get(i).toString(), WordOutline::class.java)
                    wordsList.add(wordOutline)
                }
                val simpleTraget: SimpleTarget<Drawable> = object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        controller.setBackground(resource)
                    }
                }

                Glide.with(this@SketchPadActivity).load(wordsList.get(0).wordUrl).into(simpleTraget)

            }

            override fun onFailure(responseObj: Any) {
                Log.e("SektchPadActivity", responseObj.toString())
                showToast("网络错误")
            }

        })

    }
}

