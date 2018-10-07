package com.barackbao.aicalligraphy.network.callback;

import android.hardware.display.VirtualDisplay;

import com.barackbao.aicalligraphy.model.CopyBook;
import com.barackbao.baselib.okhttp.listener.CommonCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public abstract class CopyBookListCallback extends CommonCallback<List<CopyBook>> {

    @Override
    public List<CopyBook> parseNetworkResponse(Response response, int id) throws IOException {
        String str = response.body().string();
        List<CopyBook> list = new Gson().fromJson(str, List.class);
        return list;
    }
}
