package code.java.io.file.utils;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.book.liao.data.TableOfContentItem;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.List;

public class BookSeperatedListWriter {

    private final BookSeperatedListBuilder.BookSeperatedList srcTOCList;

    private final String outputDir;

    public BookSeperatedListWriter(BookSeperatedListBuilder.BookSeperatedList srcTOCList, String outputDir) {
        this.srcTOCList = srcTOCList;
        this.outputDir = outputDir;
    }

    public void write() throws IOException {
        saveTablesOfContentsTo(srcTOCList, new File(outputDir));
    }

    private void saveTablesOfContentsTo(BookSeperatedListBuilder.BookSeperatedList srcTOCList, File toDir) throws IOException {
        for (BookTableOfContentAndBody tableOfContent : srcTOCList.getTableOfContentList()) {
            File toSubDir = new File(toDir, tableOfContent.getBookParentName());
            FileUtils.makeDirIfDoesNotExist(toSubDir);
            File tableOfContentDestFile = new File(toSubDir, tableOfContent.getBookFileName());
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(tableOfContentDestFile)));
                saveTablesOfContentsTo(tableOfContent, pw);
            } finally {
                IOUtils.closeQuietly(pw);
            }
        }
    }

    public static final String TABLE_OF_CONTENT_SEPARATOR = ",";
    public static final String PREFIX_LESS_THAN_TEN = "0";
    public static final String PREFIX_LESS_THAN_ONE_HUNDRED = "00";

    private static final StringBuffer mSbTemp = new StringBuffer();

    private void saveTablesOfContentsTo(BookTableOfContentAndBody toc, PrintWriter pw) throws IOException {
        pw.println(toc.getBookName());//写入书名
        pw.println(toc.getTableOfContentTitle());//写入“目录”二字
        pw.println();//写空行
        List<TableOfContentItem> tociList = toc.getTableOfContentItemList();
        TableOfContentItem toci = null;
        if (tociList != null && tociList.size() > 0) {
            if (tociList.size() < 100) {
                for (int i = 0; i < tociList.size(); i++) {
                    toci = tociList.get(i);
                    int titleOrder = toci.getOrder();
                    //目录一般是用空格隔开
                    mSbTemp.setLength(0);
                    if (titleOrder < 10) {
                        mSbTemp.append(PREFIX_LESS_THAN_TEN);
                    }
                    mSbTemp.append(titleOrder)
                            .append(TABLE_OF_CONTENT_SEPARATOR)
                            .append(toci.getArticleTitle());
                    pw.println(mSbTemp.toString());
                }
            } else {
                for (int i = 0; i < tociList.size(); i++) {
                    toci = tociList.get(i);
                    int titleOrder = toci.getOrder();
                    //目录一般是用空格隔开
                    mSbTemp.setLength(0);
                    if (titleOrder < 10) {
                        mSbTemp.append(PREFIX_LESS_THAN_ONE_HUNDRED);
                    } else if (titleOrder < 100) {
                        mSbTemp.append(PREFIX_LESS_THAN_TEN);
                    }
                    mSbTemp.append(titleOrder)
                            .append(TABLE_OF_CONTENT_SEPARATOR)
                            .append(toci.getArticleTitle());
                    pw.println(mSbTemp.toString());
                }
            }
        }
        List<String> descriptionList = toc.getTableOfContentDescriptionList();
        if (descriptionList != null) {
            pw.println();//打印空行
            for (String description : descriptionList) {
                pw.println(description);//打印目录描述
            }
        }
    }
}
