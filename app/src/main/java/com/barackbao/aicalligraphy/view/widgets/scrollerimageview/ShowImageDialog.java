package com.barackbao.aicalligraphy.view.widgets.scrollerimageview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.util.DisplayUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowImageDialog extends Dialog {
    private View mView;
    private Context mContext;
    private ShowImageAdapter mAdapter;
    private ShowImageViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mImgUrls;
    private List<View> mViews;

    public ShowImageDialog(@NonNull Context context, List<String> imgUrls) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        initView();
        initData();
    }


    private void initData() {
        PhotoViewAttacher.OnPhotoTapListener listener = new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        };

        for (int i = 0; i < mImgUrls.size(); i++) {
            final PhotoView photoView = new PhotoView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(params);
            photoView.setOnPhotoTapListener(listener);
            Glide.with(mContext)
                    .load(mImgUrls.get(i))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            photoView.setImageDrawable(resource);
                        }
                    });
            mViews.add(photoView);
        }
        mAdapter = new ShowImageAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mIndexText.setText(1 + "/" + mImgUrls.size());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndexText.setText(position + 1 + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.brower_dialog_image, null);
        mViewPager = mView.findViewById(R.id.show_image_vp);
        mIndexText = mView.findViewById(R.id.image_index_tv);
        mViews = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = DisplayUtil.EXACT_SCREEN_HEIGHT;
        params.width = DisplayUtil.EXACT_SCREEN_WIDTH;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

}
