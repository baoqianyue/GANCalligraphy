package com.barackbao.aicalligraphy.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CopyBook {
    private String coverImgUrl;
    private String author;
    private String copyBookName;
    //详情图片list
    private ArrayList<String> contentImgUrl;

    public CopyBook(String coverImgUrl, String author, String copyBookName) {
        this.coverImgUrl = coverImgUrl;
        this.author = author;
        this.copyBookName = copyBookName;
    }

    public ArrayList<String> getContentImgUrl() {
        return contentImgUrl;
    }

    public void setContentImgUrl(ArrayList<String> contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyBookName() {
        return copyBookName;
    }

    public void setCopyBookName(String copyBookName) {
        this.copyBookName = copyBookName;
    }

}
