package com.barackbao.aicalligraphy.mvp.presenter;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.model.FriendsCircleItem;
import com.barackbao.aicalligraphy.model.User;
import com.barackbao.aicalligraphy.mvp.contract.GenBookContract;

import java.util.ArrayList;

public class GenBookPresenter implements GenBookContract.IPresenter {

    private ArrayList<String> urls = new ArrayList<>();

    private GenBookContract.IView genBookView;

    private ArrayList<FriendsCircleItem> dataList = new ArrayList<>();


    public GenBookPresenter(GenBookContract.IView view) {
        genBookView = view;
        testData();
    }

    private void testData() {
        urls.add(String.valueOf(R.drawable.barack));
        urls.add(String.valueOf(R.drawable.barack));
        urls.add(String.valueOf(R.drawable.barack));
        urls.add(String.valueOf(R.drawable.barack));
        urls.add(String.valueOf(R.drawable.barack));
        for (int i = 0; i < 10; i++) {
            dataList.add(new FriendsCircleItem(new User("barack"), "10月1日",
                    "我爱祖国", urls, 34, 12));
        }
    }

    @Override
    public void requestData() {
        genBookView.showFriendsContentList(dataList);
    }

    @Override
    public void loadMoreData() {
        genBookView.showMoreFriendsContentList(dataList);
    }
}
