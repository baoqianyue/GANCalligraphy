package com.barackbao.aicalligraphy.view.fragment.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.activity.copybook.ShowImageActivity
import com.barackbao.aicalligraphy.adapter.CopyBookAdapter
import com.barackbao.aicalligraphy.model.CopyBook
import com.barackbao.aicalligraphy.mvp.contract.CopyBookContract
import com.barackbao.aicalligraphy.mvp.presenter.CopyBookPresenter
import com.barackbao.aicalligraphy.showToast
import com.barackbao.aicalligraphy.toActivityWithSerializable
import com.barackbao.aicalligraphy.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_copybook_layout.*
import java.util.ArrayList

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   : 首页字帖库fragment
 *     version: 1.0
 * </pre>
 */
class CopyBookFragment : BaseFragment(), CopyBookContract.IView, SwipeRefreshLayout.OnRefreshListener {

    var mDataList: List<CopyBook>? = null
    lateinit var mContentView: View

    val copyBookPresenter: CopyBookPresenter = CopyBookPresenter(this)

    val adapter by lazy {
        CopyBookAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity
        mContentView = inflater.inflate(R.layout.fragment_copybook_layout, container, false)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(mContext, 2)
        copybook_rv.layoutManager = gridLayoutManager
        copybook_rv.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        copybook_rv.adapter = adapter
        copybook_swipe_rv.setColorSchemeColors(Color.parseColor("#fb7299"))
        copybook_swipe_rv.setOnRefreshListener(this)
        copybook_rv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisibleItem: Int? = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem!! + 1 == adapter?.itemCount) {
                    copyBookPresenter.loadMoreData()
                }

            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })
        copyBookPresenter.requestData()
        adapter.onClick = { copyBook -> activity?.toActivityWithSerializable<ShowImageActivity>(copyBook) }

    }

    override fun onRefresh() {
        copybook_swipe_rv.isRefreshing = false
        copyBookPresenter.requestData()
    }

    override fun showCopyBookList(list: List<CopyBook>?) {
        if (list != null) {
            adapter.setData(list)
        }
    }

    override fun showMoreBookList(list: List<CopyBook>?) {
        if (null != list) {
            adapter.setData(list)
        }
    }


    override fun showError() {
        showToast("网络错误")
    }

}