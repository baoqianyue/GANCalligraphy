package com.barackbao.aicalligraphy.model;

import java.util.ArrayList;

/**
 * the bean of user
 */
public class User {

    //用户名
    private String userName;
    //密码
    private String userPassword;
    //用户头像
    private String userAvatar;
    //用户年龄段
    private int userAge;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
