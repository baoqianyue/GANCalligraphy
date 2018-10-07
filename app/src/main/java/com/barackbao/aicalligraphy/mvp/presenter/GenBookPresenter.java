package com.barackbao.aicalligraphy.mvp.presenter;

import com.barackbao.aicalligraphy.model.FriendsCircle;
import com.barackbao.aicalligraphy.mvp.contract.GenBookContract;
import com.barackbao.aicalligraphy.network.RequestCenter;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GenBookPresenter implements GenBookContract.IPresenter {

    private GenBookContract.IView genBookView;

    private ArrayList<FriendsCircle> dataList = new ArrayList<>();


    public GenBookPresenter(GenBookContract.IView view) {
        genBookView = view;
    }


    @Override
    public void requestData() {
        RequestCenter.requestAllFriendCircle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                JSONArray jsonArray = (JSONArray) responseObj;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        FriendsCircle item = new Gson().fromJson(jsonArray.get(i).toString(), FriendsCircle.class);
                        dataList.add(item);
                        genBookView.showFriendsContentList(dataList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Object responseObj) {
                genBookView.showError();
            }
        });
    }

    @Override
    public void loadMoreData() {
        genBookView.showMoreFriendsContentList(dataList);
    }
}
