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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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

    private ArrayList<CopyBook> dataList = new ArrayList<>();


    public CopyBookPresenter(CopyBookContract.IView view) {
        copyBookView = view;
        testData();
    }

    private void testData() {
        for (int i = 0; i < 8; i++) {
            dataList.add(new CopyBook("R.drawable.barack", "颜真卿", "多宝塔碑" + i));
        }
    }

    @Override
    public void requestData() {
        RequestCenter.requestAllCopyBook(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e(TAG, "onSuccess: " + responseObj.toString());
            }

            @Override
            public void onFailure(Object responseObj) {
                Log.e(TAG, "onFailure: " + responseObj.toString());
                copyBookView.showError();
            }
        });

//        OkHttpClient client = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        final Request request = builder.get().url(HttpConstants.COPYBOOKALL).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, "onFailure: " + e);
//                copyBookView.showError();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, "onResponse: " + response.toString());
//            }
//        });
        copyBookView.showCopyBookList(dataList);
    }

    @Override
    public void loadMoreData() {
        for (int i = 0; i < 8; i++) {
            dataList.add(new CopyBook("R.drawable.barack", "颜真卿", "多宝塔碑" + i));
        }
        copyBookView.showCopyBookList(dataList);
    }


}
