package com.barackbao.aicalligraphy.mvp.contract;

import com.barackbao.aicalligraphy.model.FriendsCircle;
import com.barackbao.aicalligraphy.mvp.base.BasePresenter;
import com.barackbao.aicalligraphy.mvp.base.BaseView;

import java.util.ArrayList;

public interface GenBookContract {

    interface IView extends BaseView<IPresenter> {

        void showFriendsContentList(ArrayList<FriendsCircle> friendsCircleList);

        void showMoreFriendsContentList(ArrayList<FriendsCircle> friendsCircleList);

        void showError();

    }

    interface IPresenter extends BasePresenter {
        void requestData();

        void loadMoreData();
    }
}
