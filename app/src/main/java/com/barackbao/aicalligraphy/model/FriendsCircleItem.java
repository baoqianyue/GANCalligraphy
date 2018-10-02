package com.barackbao.aicalligraphy.model;


import java.util.ArrayList;

/**
 * the bean of calligraphy friend circle item
 */
public class FriendsCircleItem {

    //发布用户 not null
    private User user;
    //发布日期 not null
    private String releaseDate;
    //发布内容文字描述 can null
    private String itemText;
    //发布的图片url can null
    private ArrayList<String> imgUrls;
    //用户发布生成的碑帖 can null，当发布内容不是朋友圈动态时表示发布内容为用户生成的碑帖
    private Stick stick;
    //点赞数
    private int likeNum;
    //分享数
    private int shareNum;

    public FriendsCircleItem(User user, String releaseDate, String itemText,
                             ArrayList<String> imgUrls, int likeNum, int shareNum) {
        this.user = user;
        this.releaseDate = releaseDate;
        this.itemText = itemText;
        this.imgUrls = imgUrls;
        this.likeNum = likeNum;
        this.shareNum = shareNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        itemText = itemText;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Stick getStick() {
        return stick;
    }

    public void setStick(Stick stick) {
        this.stick = stick;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }


}
