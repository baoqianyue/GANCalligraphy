package com.barackbao.baselib.okhttp.request;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装请求参数
 */
public class RequestParams {

    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();


    public RequestParams() {
        this(null);
    }


    public RequestParams(Map<String, String> source) {
        if (null != source) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }


    /**
     * 向urlParams中添加参数
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        if (null != key && null != value) {
            urlParams.put(key, value);
        }
    }

    /**
     * 想fileParams中添加参数
     *
     * @param key
     * @param value
     * @throws FileNotFoundException
     */
    public void put(String key, Object value) throws FileNotFoundException {
        if (null != key) {
            fileParams.put(key, value);
        }
    }

    public boolean hasParams() {
        if (urlParams.size() > 0 || fileParams.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


}