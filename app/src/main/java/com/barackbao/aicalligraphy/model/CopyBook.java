package com.barackbao.aicalligraphy.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   : 对应CopyBook表
 *     version: 1.0
 * </pre>
 */
public class CopyBook {
    //封面img url
    private String coverImgUrl;
    //书法家名
    private String author;
    //碑帖名
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
