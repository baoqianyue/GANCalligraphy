package com.barackbao.aicalligraphy.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.barackbao.aicalligraphy.model.FriendsCircleItem
import com.barackbao.aicalligraphy.view.widgets.FriendCircleItem

class GenBookAdapter : RecyclerView.Adapter<GenBookAdapter.ViewHolder>() {


    val data by lazy {
        ArrayList<FriendsCircleItem>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = FriendCircleItem(parent.context)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as FriendCircleItem).setData(data[position])
        holder.itemView.setOnClickListener { onClick?.invoke(data[position]) }
    }

    fun setData(data: ArrayList<FriendsCircleItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<FriendsCircleItem>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    var onClick: ((FriendsCircleItem) -> Unit)? = {}

}