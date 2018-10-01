package com.barackbao.aicalligraphy.mvp.contract;

import com.barackbao.aicalligraphy.model.FriendsCircleItem;
import com.barackbao.aicalligraphy.mvp.base.BasePresenter;
import com.barackbao.aicalligraphy.mvp.base.BaseView;

import java.util.ArrayList;

public interface GenBookContract {

    interface IView extends BaseView<IPresenter> {

        void showFriendsContentList(ArrayList<FriendsCircleItem> friendsCircleList);

        void showMoreFriendsContentList(ArrayList<FriendsCircleItem> friendsCircleList);

        void showError();

    }

    interface IPresenter extends BasePresenter {
        void requestData();

        void loadMoreData();
    }
}
