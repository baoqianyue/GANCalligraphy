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

    public static void getRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params), new DisposeDataHandle(listener, clazz));
    }


    public static void requestAllCopyBook(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.COPYBOOKALL, null, listener, null);
    }

    public static void register(DisposeDataListener listener, RequestParams params) {
        RequestCenter.postRequest(HttpConstants.REGISTER, params, listener, null);
    }

    public static void requestAllFriendCircle(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.FRIENDCIRCLEALL, null, listener, null);
    }

    public static void requestWordsOutline(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.WORDSOUTLINE, null, listener, null);
    }

    public static void requestTestWords(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.FINDWORDS, null, listener, null);
    }

    public static void login(DisposeDataListener listener, RequestParams params) {
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, null);
    }


}
