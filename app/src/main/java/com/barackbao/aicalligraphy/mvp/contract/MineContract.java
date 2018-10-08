package com.barackbao.aicalligraphy.mvp.contract;

import com.barackbao.aicalligraphy.mvp.base.BasePresenter;
import com.barackbao.aicalligraphy.mvp.base.BaseView;

public interface MineContract {

    interface IView extends BaseView<MineContract.IPresenter> {
        void showError();

        void showPersonMessage();
    }

    interface IPresenter extends BasePresenter {
        void requestPersonMessage();

    }
}
