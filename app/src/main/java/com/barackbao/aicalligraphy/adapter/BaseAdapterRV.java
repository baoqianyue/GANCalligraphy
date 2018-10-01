package com.barackbao.aicalligraphy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.barackbao.aicalligraphy.view.holder.BaseHolderRV;

import java.util.List;

public abstract class BaseAdapterRV<T> extends RecyclerView.Adapter {

    private Context context;

    public List<T> listData;

    public BaseAdapterRV(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseHolderRV<T> baseHolder = (BaseHolderRV<T>) holder;
        T bean = getItem(position);
        baseHolder.refreshView(bean, position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    private T getItem(int position) {
        return listData.get(position);
    }

    public abstract BaseHolderRV<T> createViewHolder(Context context, ViewGroup parent, int viewType);

    public void setDatas(List<T> newData) {
        this.listData = newData;
        notifyDataSetChanged();
    }

    public void remove(T bean) {
        listData.remove(bean);
        notifyDataSetChanged();
    }
}
