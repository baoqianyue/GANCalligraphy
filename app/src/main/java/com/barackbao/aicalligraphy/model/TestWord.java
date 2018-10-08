package com.barackbao.aicalligraphy.model;

public class TestWord {


    /**
     * FindWordsId : 1
     * FindAuthorName : 柳公权
     * FindWordName : 丹
     * WordsUrl : http://39.105.110.19/static/images/recognition/柳公权-丹.PNG
     * Probability : 58.6%
     */

    private String FindWordsId;
    private String FindAuthorName;
    private String FindWordName;
    private String WordsUrl;
    private String Probability;

    public String getFindWordsId() {
        return FindWordsId;
    }

    public void setFindWordsId(String FindWordsId) {
        this.FindWordsId = FindWordsId;
    }

    public String getFindAuthorName() {
        return FindAuthorName;
    }

    public void setFindAuthorName(String FindAuthorName) {
        this.FindAuthorName = FindAuthorName;
    }

    public String getFindWordName() {
        return FindWordName;
    }

    public void setFindWordName(String FindWordName) {
        this.FindWordName = FindWordName;
    }

    public String getWordsUrl() {
        return WordsUrl;
    }

    public void setWordsUrl(String WordsUrl) {
        this.WordsUrl = WordsUrl;
    }

    public String getProbability() {
        return Probability;
    }

    public void setProbability(String Probability) {
        this.Probability = Probability;
    }
}
