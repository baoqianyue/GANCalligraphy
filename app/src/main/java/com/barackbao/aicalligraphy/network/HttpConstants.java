package com.barackbao.aicalligraphy.network;

import com.barackbao.baselib.okhttp.CommonOkHttpClient;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.barackbao.baselib.okhttp.request.RequestParams;

public class HttpConstants {

    private static final String ROOT_URL = "http://39.105.110.19";

    public static final String COPYBOOKALL = ROOT_URL + "/copybookList";

    public static final String REGISTER = ROOT_URL + "/register/?";

    public static final String FRIENDCIRCLEALL = ROOT_URL + "/showFriends";

    public static final String WORDSOUTLINE = ROOT_URL + "/WordsOutline";

    public static final String FINDWORDS = ROOT_URL + "/findwords";

    public static final String LOGIN = ROOT_URL + "/login/?";

}
