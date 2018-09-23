package com.barackbao.aicalligraphy.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.view.fragment.BaseFragment

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GenBookFragment : BaseFragment() {

    lateinit var mContentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        mContentView = inflater.inflate(R.layout.fragment_genbook_layout, container, false)
        return mContentView
    }
}