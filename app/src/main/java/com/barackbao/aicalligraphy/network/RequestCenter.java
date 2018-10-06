package com.barackbao.aicalligraphy.network;

import com.barackbao.baselib.okhttp.CommonOkHttpClient;
import com.barackbao.baselib.okhttp.listener.DisposeDataHandle;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.barackbao.baselib.okhttp.request.CommonRequest;
import com.barackbao.baselib.okhttp.request.RequestParams;

/**
 * api
 */
public class RequestCenter {

    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }


    public static void requestAllCopyBook(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.COPYBOOKALL, null, listener, null);
    }
}
