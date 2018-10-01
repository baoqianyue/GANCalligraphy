package com.barackbao.aicalligraphy.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.barackbao.aicalligraphy.view.holder.BaseHolderRV;
import com.barackbao.aicalligraphy.view.holder.ImageHolder;

import java.util.List;

public class ImageAdapter extends BaseAdapterRV<String> {


    public ImageAdapter(Context context, List<String> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderRV<String> createViewHolder(Context context, ViewGroup parent, int viewType) {
        return new ImageHolder(context, parent, this, viewType);
    }
}
