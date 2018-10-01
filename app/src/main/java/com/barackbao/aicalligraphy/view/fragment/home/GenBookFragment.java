package com.barackbao.aicalligraphy.view.fragment.home;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.util.DisplayUtil;
import com.barackbao.aicalligraphy.view.fragment.BaseFragment;

public class GenBookFragment extends BaseFragment {

    private ActionBar toolbar;
    private TextView likeTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genbook_layout, container, false);
        toolbar = getActivity().getActionBar();
        likeTv = view.findViewById(R.id.like_tv);
        return view;
    }

    public void animateUp(int[] loactions) {
        int currentY = loactions[1] - DisplayUtil.dp2px(24);
        likeTv.setVisibility(View.VISIBLE);
        likeTv.setTranslationX(loactions[0]);
        likeTv.setTranslationY(currentY);
        likeTv.setScaleY(1);
        likeTv.setScaleX(1);
        likeTv.setAlpha(1f);

        int top = currentY - DisplayUtil.dp2px(30);
        likeTv.animate().alpha(0).translationY(top)
                .setInterpolator(new DecelerateInterpolator())
                .scaleX(1.2f).scaleY(1.2f).setDuration(1000);
    }
}
