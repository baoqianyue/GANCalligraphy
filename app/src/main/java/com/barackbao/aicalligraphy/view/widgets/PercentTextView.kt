package com.barackbao.aicalligraphy.view.widgets

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.WindowManager
import com.barackbao.aicalligraphy.R

class PercentTextView : AppCompatTextView {

    private var mTextSizePercent = 1f

    companion object {
        private var baseScreenHeight = 1920

        /**
         * get current screen width and height to calculate the percent
         */
        fun getScreenHeight(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return windowManager.defaultDisplay.height
        }
    }

    constructor(context: Context) : super(context) {
        setDefaultPercent(context)
        textSize = textSize
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrs(context, attrs)
        setDefaultPercent(context)
        textSize = textSize
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
        setDefaultPercent(context)
        textSize = textSize
    }

    override fun setTextSize(unit: Int, size: Float) {
        var varSize = size
        varSize = (varSize * mTextSizePercent).toInt().toFloat()
        super.setTextSize(unit, varSize)
    }

    override fun setPaintFlags(flags: Int) {
        super.setPaintFlags(flags)
    }

    override fun setTextSize(size: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    var textSizePercent: Float
        get() = mTextSizePercent
        set(textSizePercent) {
            mTextSizePercent = textSizePercent
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

    fun setTextSizePercent(unit: Int, textSizePercent: Float) {
        mTextSizePercent = textSizePercent
        setTextSize(unit, textSize)
    }

    private fun getAttrs(context: Context, attrs: AttributeSet) {
        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.PercentTextView)
        baseScreenHeight = customAttrs.getInt(R.styleable.PercentTextView_baseScreenHeight, baseScreenHeight)
        customAttrs.recycle()
    }

    private fun setDefaultPercent(context: Context) {
        val screenHeight = getScreenHeight(context).toFloat()
        mTextSizePercent = screenHeight / baseScreenHeight
    }
}