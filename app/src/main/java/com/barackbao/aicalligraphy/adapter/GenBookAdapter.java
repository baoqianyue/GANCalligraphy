package com.barackbao.aicalligraphy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.activity.HomeActivity;
import com.barackbao.aicalligraphy.model.FriendsCircleItem;
import com.barackbao.aicalligraphy.mvp.base.BaseView;
import com.barackbao.aicalligraphy.view.fragment.home.GenBookFragment;
import com.barackbao.aicalligraphy.view.holder.BaseHolderRV;


public class GenBookAdapter extends RecyclerView.Adapter<GenBookAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends BaseHolderRV<FriendsCircleItem> {

        private ImageView userAvatarImg;
        private TextView userNameTv;
        private TextView releaseDateTv;
        private TextView itemTextTv;
        private RecyclerView contentImgRv;
        private TextView itemShareNumTv;
        private TextView itemCommentTv;
        private CheckBox itemLikeCb;
        private TextView itemLikeNumTv;
        private GridLayoutManager layoutManager;
        private ImageAdapter imageAdapter;

        public ViewHolder(Context context, ViewGroup parent,
                          BaseAdapterRV<FriendsCircleItem> adapter,
                          int itemType) {
            super(context, parent, adapter, itemType, R.layout.item_friends_circle);
        }

        @Override
        protected void initView(View itemView) {
            userAvatarImg = itemView.findViewById(R.id.user_avatar_img);
            userNameTv = itemView.findViewById(R.id.user_name_tv);
            releaseDateTv = itemView.findViewById(R.id.release_date_tv);
            itemTextTv = itemView.findViewById(R.id.item_text_tv);
            contentImgRv = itemView.findViewById(R.id.content_img_rv);
            itemShareNumTv = itemView.findViewById(R.id.item_share_num_tv);
            itemCommentTv = itemView.findViewById(R.id.item_comment_tv);
            itemLikeCb = itemView.findViewById(R.id.item_like_cb);
            itemLikeNumTv = itemView.findViewById(R.id.item_like_num_tv);
            layoutManager = new GridLayoutManager(mContext, 3);
            contentImgRv.setLayoutManager(layoutManager);
            imageAdapter = new ImageAdapter(mContext, null);
            contentImgRv.setAdapter(imageAdapter);

            itemLikeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        int [] locations = new int[2];
                        itemLikeNumTv.getLocationInWindow(locations);
                        ((GenBookFragment) mContext).animateUp(locations);
                    }
                }
            });
        }

        @Override
        protected void onRefreshView(FriendsCircleItem bean, int position) {

        }
    }

}
