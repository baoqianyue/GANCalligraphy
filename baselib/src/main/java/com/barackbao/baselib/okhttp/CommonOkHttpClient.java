package com.barackbao.baselib.okhttp;

import com.barackbao.baselib.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * 网络请求参数配置
 */
public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;

    //为所有的okhttpclient配置相同参数
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //允许请求重定向
        builder.followRedirects(true);

        //https支持
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        //获取ssl证书
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory());
        //生成client
        mOkHttpClient = builder.build();
    }

    public static void sendRequest()
}
