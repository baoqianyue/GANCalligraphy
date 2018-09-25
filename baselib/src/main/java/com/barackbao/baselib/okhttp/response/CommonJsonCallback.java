package com.barackbao.baselib.okhttp.response;


import android.os.Handler;
import android.os.Looper;

import com.barackbao.baselib.okhttp.exception.OkHttpException;
import com.barackbao.baselib.okhttp.listener.DisposeDataHandle;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 处理json的回调响应
 */
public class CommonJsonCallback implements Callback {

    //todo 状态码要和服务器保持一致
    protected final String RESULT_CODE = "ecode";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    //自定义错误类型
    protected final int NETWORK_ERROR = -1; //网络错误
    protected final int JSON_ERROR = -2; //json错误
    protected final int OTHER_ERROR = -3; //其他错误

    private Handler mDeliveryHandler; //消息转发handle，切换到ui线程
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        //切换为主线程
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String result = response.body().string();

        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }


    /**
     * 处理response
     *
     * @param responseObj
     */
    private void handleResponse(Object responseObj) {

        if (null == responseObj && responseObj.toString().trim().equals("")) {
            //如果为空，认为网络错误
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
                //从json中获取状态码进行比对
                if (RESULT_CODE_VALUE == result.getInt(RESULT_CODE)) {
                    //判断是否返回了实体对应的字节码
                    if (null == mClass) {
                        //如果字节码为空，直接将json字符串回调
                        mListener.onSuccess(responseObj);
                    } else {
                        //将json转为实体对象
                        Gson gson = new Gson();
                        Object obj = gson.fromJson(String.valueOf(result), mClass);
                        if (null != obj) {
                            mListener.onSuccess(obj);
                        } else {
                            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                } else {
                    //将服务器的异常回调到应用成去处理
                    //将状态码回调
                    mListener.onFailure(new OkHttpException(OTHER_ERROR, result.get(RESULT_CODE)));
                }
            }
        } catch (Exception e) {
            //如果到了catch快，每一层都有可能有问题，将异常回调到应用层去
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }
}

