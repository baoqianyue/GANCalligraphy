package com.barackbao.baselib.okhttp.listener;


/**
 * 处理回调返回的json数据，通过返回的字节码生成对应对象，如果没有字节码返回，就直接返回json
 */
public class DisposeDataHandle {

    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class clazz) {
        mListener = listener;
        mClass = clazz;
    }


}
