package com.barackbao.aicalligraphy.model;

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
    private String desciption;

    public CopyBook(String coverImgUrl, String author, String copyBookName) {
        this.coverImgUrl = coverImgUrl;
        this.author = author;
        this.copyBookName = copyBookName;
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
