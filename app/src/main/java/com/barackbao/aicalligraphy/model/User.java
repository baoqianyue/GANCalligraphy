package com.barackbao.aicalligraphy.model;

import java.util.ArrayList;

/**
 * the bean of user
 */
public class User {

    //用户名
    private String UserName;
    //密码
    private String UserPassword;
    //用户头像
    private String UserAvatar;
    //用户年龄段
    private String UserAge;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserAvatar() {
        return UserAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        UserAvatar = userAvatar;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }
}
