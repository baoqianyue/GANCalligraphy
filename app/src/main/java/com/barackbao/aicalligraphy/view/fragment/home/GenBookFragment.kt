package com.barackbao.aicalligraphy.view.fragment.home

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast

import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.adapter.GenBookAdapter
import com.barackbao.aicalligraphy.model.FriendsCircleItem
import com.barackbao.aicalligraphy.mvp.contract.GenBookContract
import com.barackbao.aicalligraphy.mvp.presenter.GenBookPresenter
import com.barackbao.aicalligraphy.util.DisplayUtil
import com.barackbao.aicalligraphy.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_genbook_layout.*

import java.util.ArrayList

class GenBookFragment : BaseFragment(), GenBookContract.IView, SwipeRefreshLayout.OnRefreshListener {

    lateinit var mContentView: View

    val presenter: GenBookPresenter = GenBookPresenter(this)
    val adapter by lazy {
        GenBookAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_genbook_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friend_circle_rv.layoutManager = LinearLayoutManager(context)
        friend_circle_rv.adapter = adapter
        friend_circle_rv.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        friend_circle_swipe_rv.setColorSchemeColors(Color.parseColor("#fb7299"))
        friend_circle_swipe_rv.setOnRefreshListener(this)
        friend_circle_rv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            internal var lastVisibleItem = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter!!.itemCount) {
                    presenter!!.loadMoreData()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView!!.layoutManager as LinearLayoutManager
                manager.findLastVisibleItemPosition()
            }
        })
        adapter.onClick = { FriendsCircleItem -> Toast.makeText(context, "Onclick", Toast.LENGTH_SHORT) }
        presenter.requestData()
    }

    override fun showFriendsContentList(friendsCircleList: ArrayList<FriendsCircleItem>?) {
        if (friendsCircleList != null) {
            adapter!!.setData(friendsCircleList)
        }
    }

    override fun showMoreFriendsContentList(friendsCircleList: ArrayList<FriendsCircleItem>) {

    }

    override fun showError() {

    }

    override fun onRefresh() {
        friend_circle_swipe_rv.isRefreshing = false
        presenter.requestData()
    }
}
