package com.barackbao.aicalligraphy.view.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.adapter.BaseAdapterRV;
import com.barackbao.aicalligraphy.util.DisplayUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageHolder extends BaseHolderRV<String> {

    private ImageView img;

    public ImageHolder(Context context, ViewGroup parent, BaseAdapterRV<String> adapter, int itemType) {
        super(context, parent, adapter, itemType, R.layout.item_image);
    }

    @Override
    protected void initView(View itemView) {
        img = itemView.findViewById(R.id.img);
    }

    /**
     * refresh the position of sub widgets
     *
     * @param imagePath
     * @param position
     */
    @Override
    protected void onRefreshView(String imagePath, int position) {
        final ViewGroup.LayoutParams params = super.itemView.getLayoutParams();
        if (super.adapter.getItemCount() == 1) {
            Glide.with(mContext).asBitmap().load(imagePath).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    params.width = resource.getWidth();
                    params.height = resource.getHeight();
                    return false;
                }
            });
        } else {
            params.width = DisplayUtil.getGridWidth();
            params.height = DisplayUtil.getGridWidth();
        }
    }
}
