package com.barackbao.aicalligraphy.view.fragment.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.adapter.CopyBookAdapter
import com.barackbao.aicalligraphy.model.CopyBook
import com.barackbao.aicalligraphy.mvp.contract.CopyBookContract
import com.barackbao.aicalligraphy.mvp.presenter.CopyBookPresenter
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
class CopyBookFragment : BaseFragment(), CopyBookContract.IView {


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
        adapter.onClick = { copyBook -> Toast.makeText(context, "dianji copybook${copyBook.copyBookName}", Toast.LENGTH_SHORT) }
        copyBookPresenter.requestData()
    }

    override fun showCopyBookList(list: ArrayList<CopyBook>?) {
        if (list != null) {
            adapter.setData(list)
        }
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}