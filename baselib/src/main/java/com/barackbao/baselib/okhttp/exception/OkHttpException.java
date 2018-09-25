package com.barackbao.baselib.okhttp.exception;

/**
 * 网络请求一异常定义
 */
public class OkHttpException extends Exception {

    private int eCode; //返回的错误码
    private Object eMsg; //返回的错误信息

    public OkHttpException(int eCode, Object eMsg) {
        this.eCode = eCode;
        this.eMsg = eMsg;
    }

    public int geteCode() {
        return eCode;
    }

    public Object geteMsg() {
        return eMsg;
    }
}
