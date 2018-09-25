package com.barackbao.baselib.okhttp.listener;


/**
 * 请求监听
 */
public interface DisposeDataListener {

    public void onSuccess(Object responseObj);

    public void onFailure(Object responseObj);

}
