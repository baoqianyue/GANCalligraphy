package com.barackbao.aicalligraphy.model;


import java.util.List;

/**
 * the bean of calligraphy friend circle item
 */
public class FriendsCircle {


    /**
     * UserName : U16813
     * UserAvatar : http://39.105.110.19/media/media/2.png
     * friends_circle_item : [{"user":"U16813","releaseDate":"2018-10-06T12:21:23Z","ItemText":"行草北山移文",
     * "imgUrl":"http://39.105.110.19/static/images/赵孟頫/洛神赋/fatie-002.jpg","stick":"0","likeNum":"19","shareNum":"2"}]
     */

    private String UserName;
    private String UserAvatar;
    private List<FriendsCircleItemBean> friends_circle_item;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserAvatar() {
        return UserAvatar;
    }

    public void setUserAvatar(String UserAvatar) {
        this.UserAvatar = UserAvatar;
    }

    public List<FriendsCircleItemBean> getFriends_circle_item() {
        return friends_circle_item;
    }

    public void setFriends_circle_item(List<FriendsCircleItemBean> friends_circle_item) {
        this.friends_circle_item = friends_circle_item;
    }

    public static class FriendsCircleItemBean {
        /**
         * user : U16813
         * releaseDate : 2018-10-06T12:21:23Z
         * ItemText : 行草北山移文
         * imgUrl : http://39.105.110.19/static/images/赵孟頫/洛神赋/fatie-002.jpg
         * stick : 0
         * likeNum : 19
         * shareNum : 2
         *
         *
         *
         *
         *
         *
         */

        private String user;
        private String releaseDate;
        private String ItemText;
        private String imgUrl;
        private String stick;
        private String likeNum;
        private String shareNum;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getItemText() {
            return ItemText;
        }

        public void setItemText(String ItemText) {
            this.ItemText = ItemText;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getStick() {
            return stick;
        }

        public void setStick(String stick) {
            this.stick = stick;
        }

        public String getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(String likeNum) {
            this.likeNum = likeNum;
        }

        public String getShareNum() {
            return shareNum;
        }

        public void setShareNum(String shareNum) {
            this.shareNum = shareNum;
        }
    }
}
