package com.barackbao.baselib.okhttp.request;


import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 接收请求参数，生成Request对象
 */
public class CommonRequest {

    /**
     * 返回一个装配好的post对象
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (null != params) {
            //将所有请求参数加入到builder中
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        //得到请求体对象
        FormBody formBody = builder.build();
        return new Request.Builder().url(url).post(formBody).build();
    }

    /**
     * 返回一个装配好的get请求对象
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder builder = new StringBuilder(url).append("?");

        if (null != params) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        return new Request.Builder().url(builder.substring(0, builder.length() - 1)).get().build();
    }


}
