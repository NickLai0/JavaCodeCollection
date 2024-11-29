package code.java.io.file.book.liao.data;

import java.util.List;

public class TableOfContent {

    //书本所属的父目录名
    private String bookParentName;
    //书本所属的文件名
    private String bookFileName;
    //书本名
    private String bookName;
    //书本描述列表
    private List<String> bookDescriptionList;
    //目录标题（一般是“目录”二字）
    private String tableOfContentTitle;
    //目录列表
    private List<TableOfContentItem> tableOfContentItemList;
    //目录描述列表
    private List<String> tableOfContentDescriptionList;

    public String getBookParentName() {
        return bookParentName;
    }

    public void setBookParentName(String bookParentName) {
        this.bookParentName = bookParentName;
    }

    public String getBookFileName() {
        return bookFileName;
    }

    public void setBookFileName(String bookFileName) {
        this.bookFileName = bookFileName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<String> getBookDescriptionList() {
        return bookDescriptionList;
    }

    public void setBookDescriptionList(List<String> bookDescriptionList) {
        this.bookDescriptionList = bookDescriptionList;
    }

    public String getTableOfContentTitle() {
        return tableOfContentTitle;
    }

    public void setTableOfContentTitle(String tableOfContentTitle) {
        this.tableOfContentTitle = tableOfContentTitle;
    }

    public List<TableOfContentItem> getTableOfContentItemList() {
        return tableOfContentItemList;
    }

    public void setTableOfContentItemList(List<TableOfContentItem> tableOfContentItemList) {
        this.tableOfContentItemList = tableOfContentItemList;
    }

    public List<String> getTableOfContentDescriptionList() {
        return tableOfContentDescriptionList;
    }

    public void setTableOfContentDescriptionList(List<String> tableOfContentDescriptionList) {
        this.tableOfContentDescriptionList = tableOfContentDescriptionList;
    }
}
