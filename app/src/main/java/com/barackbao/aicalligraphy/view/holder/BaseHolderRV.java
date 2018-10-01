package com.barackbao.aicalligraphy.view.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barackbao.aicalligraphy.adapter.BaseAdapterRV;

public abstract class BaseHolderRV<T> extends RecyclerView.ViewHolder {

    public Context mContext;
    public ViewGroup parent;
    public BaseAdapterRV<T> adapter;
    public int position;
    public T bean;

    public BaseHolderRV(Context context, ViewGroup parent,
                        BaseAdapterRV<T> adapter,
                        int itemType, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        this.mContext = context;
        this.parent = parent;
        this.adapter = adapter;
        initView(super.itemView);
        super.itemView.setOnClickListener(mOnRootClickListener);
    }

    protected abstract void initView(View itemView);

    protected abstract void onRefreshView(T bean, int position);

    public void refreshView(T bean, int position) {
        this.bean = bean;
        this.position = position;
        onRefreshView(bean, position);
    }

    private View.OnClickListener mOnRootClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onItemClick(itemView, position, bean);
        }
    };

    private void onItemClick(View itemView, int position, T bean) {
    }
}
