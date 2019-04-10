package com.barackbao.aicalligraphy.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BarackBao on 2019/4/10;
 */
public class RequestPermission {

    public static final int PERMISSION_REQUEST_CODE = 1;

    private static OnPermissionRequestListener mListener;

    public interface OnPermissionRequestListener {
        public void onGranted();

        public void onDenied(List<String> deniedList);
    }


    public static void requestPermissions(Activity activity, String[] permissions,
                                          OnPermissionRequestListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionList.toArray(new String[permissionList.size()]),
                    PERMISSION_REQUEST_CODE);
        } else {
            listener.onGranted();
        }
    }


}
