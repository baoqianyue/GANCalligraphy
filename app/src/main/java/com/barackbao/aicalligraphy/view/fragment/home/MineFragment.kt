package com.barackbao.aicalligraphy.view.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.activity.guide.GuideActivity
import com.barackbao.aicalligraphy.mvp.contract.MineContract
import com.barackbao.aicalligraphy.showToast
import com.barackbao.aicalligraphy.toActivity
import com.barackbao.aicalligraphy.util.StaticClass
import com.barackbao.aicalligraphy.view.fragment.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_mine_layout.*

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MineFragment : BaseFragment(), MineContract.IView {

    companion object {
        public fun isLogin(): Boolean {
            return null != StaticClass.currentUserName
        }
    }

    lateinit var mContentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        mContentView = inflater.inflate(R.layout.fragment_mine_layout, container, false)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(R.drawable.barack).into(my_icon)
        if (isLogin()) {
            my_name.text = StaticClass.currentUserName
        } else {
            my_name.text = "未登录"
        }

        collection_cv.setOnClickListener {
            if (isLogin()) {
            } else {
                showToast("请先登录")
                activity?.toActivity<GuideActivity>()
                activity?.finish()
            }
        }
        my_copybook_cv.setOnClickListener {
            if (isLogin()) {

            } else {
                showToast("请先登录")
                activity?.toActivity<GuideActivity>()
                activity?.finish()
            }
        }
        practice_cv.setOnClickListener {
            if (isLogin()) {

            } else {
                showToast("请先登录")
                activity?.toActivity<GuideActivity>()
                activity?.finish()
            }
        }
        settings_cv.setOnClickListener {
            if (isLogin()) {

            } else {
                showToast("请先登录")
                activity?.toActivity<GuideActivity>()
                activity?.finish()
            }
        }
        quit_btn.setOnClickListener {
            StaticClass.currentUserName = null
            activity?.toActivity<GuideActivity>()
            activity?.finish()
        }
    }


    override fun showPersonMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}