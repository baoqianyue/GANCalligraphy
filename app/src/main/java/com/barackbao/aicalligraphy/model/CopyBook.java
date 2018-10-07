package com.barackbao.aicalligraphy.model;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   : 对应CopyBook表
 *     version: 1.0
 * </pre>
 */

public class CopyBook implements Serializable{

    /**
     * author : 文徵明
     * copyBookName : 临兰亭序
     * copy_book_all : [{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-000.jpg",
     * "CopyBookEachName":"临兰亭序","CopyBookEachId":"1"},
     * {"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-001.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"2"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-002.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"3"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-003.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"4"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-004.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"5"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-005.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"6"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-006.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"7"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-007.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"8"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-008.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"9"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-009.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"10"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-010.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"11"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-011.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"12"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-012.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"13"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-013.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"14"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-014.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"15"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-015.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"16"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-016.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"17"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-017.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"18"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-018.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"19"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-019.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"20"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-020.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"21"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-021.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"22"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-022.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"23"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-023.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"24"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-024.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"25"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-025.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"26"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-026.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"27"},{"contentImgUrl":"http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-027.jpg","CopyBookEachName":"临兰亭序","CopyBookEachId":"28"}]
     */

    private String author;
    private String copyBookName;
    private List<CopyBookAllBean> copy_book_all;

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

    public List<CopyBookAllBean> getCopy_book_all() {
        return copy_book_all;
    }

    public void setCopy_book_all(List<CopyBookAllBean> copy_book_all) {
        this.copy_book_all = copy_book_all;
    }

    public static class CopyBookAllBean implements Serializable {
        /**
         * contentImgUrl : http://39.105.110.19/static/images/文徵明/临兰亭序/fatie-000.jpg
         * CopyBookEachName : 临兰亭序
         * CopyBookEachId : 1
         */

        private String contentImgUrl;
        private String CopyBookEachName;
        private String CopyBookEachId;

        public String getContentImgUrl() {
            return contentImgUrl;
        }

        public void setContentImgUrl(String contentImgUrl) {
            this.contentImgUrl = contentImgUrl;
        }

        public String getCopyBookEachName() {
            return CopyBookEachName;
        }

        public void setCopyBookEachName(String CopyBookEachName) {
            this.CopyBookEachName = CopyBookEachName;
        }

        public String getCopyBookEachId() {
            return CopyBookEachId;
        }

        public void setCopyBookEachId(String CopyBookEachId) {
            this.CopyBookEachId = CopyBookEachId;
        }
    }
}
