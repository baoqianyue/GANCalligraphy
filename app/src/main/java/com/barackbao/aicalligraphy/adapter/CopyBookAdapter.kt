package com.barackbao.aicalligraphy.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.barackbao.aicalligraphy.model.CopyBook
import com.barackbao.aicalligraphy.view.widgets.CopyBookItem

/**
 * <pre>
 * author : baoqianyue
 * time   : 2018/09/23
 * desc   :
 * version: 1.0
</pre> *
 */
class CopyBookAdapter : RecyclerView.Adapter<CopyBookAdapter.ViewHolder>() {

    val data by lazy {
        ArrayList<CopyBook>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View? = CopyBookItem(parent.context)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as CopyBookItem).setData(data[position])
        holder.itemView.setOnClickListener { onClick?.invoke(data[position]) }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<CopyBook>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    var onClick: ((CopyBook) -> Unit)? = {}

}
