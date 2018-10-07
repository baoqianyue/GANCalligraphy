package com.barackbao.aicalligraphy.mvp.presenter;

import android.util.Log;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.model.CopyBook;
import com.barackbao.aicalligraphy.mvp.contract.CopyBookContract;
import com.barackbao.aicalligraphy.network.HttpConstants;
import com.barackbao.aicalligraphy.network.RequestCenter;
import com.barackbao.aicalligraphy.view.fragment.home.CopyBookFragment;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.barackbao.baselib.okhttp.response.CommonJsonCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;



/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CopyBookPresenter implements CopyBookContract.IPresenter {


    private static final String TAG = "CopyBookPresenter";

    private CopyBookContract.IView copyBookView;

    private List<CopyBook> dataList = new ArrayList<>();


    public CopyBookPresenter(CopyBookContract.IView view) {
        copyBookView = view;
    }

    @Override
    public void requestData() {

        RequestCenter.requestAllCopyBook(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                JSONArray resListjson = (JSONArray) responseObj;
                for (int i = 0; i < resListjson.length(); i++) {
                    try {
                        CopyBook copyBook = new Gson().fromJson(resListjson.get(i).toString(), CopyBook.class);
                        dataList.add(copyBook);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                copyBookView.showCopyBookList(dataList);
            }

            @Override
            public void onFailure(Object responseObj) {
                Log.e(TAG, "onFailure: " + responseObj.toString());
                copyBookView.showError();
            }
        });

    }

    @Override
    public void loadMoreData() {
        copyBookView.showCopyBookList(dataList);
    }


}
