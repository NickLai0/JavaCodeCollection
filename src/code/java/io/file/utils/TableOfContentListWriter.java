package code.java.io.file.utils;

import code.java.io.file.book.liao.builder.TableOfContentListBuilder;
import code.java.io.file.book.liao.data.TableOfContent;
import code.java.io.file.book.liao.data.TableOfContentItem;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.List;

public class TableOfContentListWriter {

    private final TableOfContentListBuilder.TableOfContentList srcTOCList;

    private final String outputDir;

    public TableOfContentListWriter(TableOfContentListBuilder.TableOfContentList srcTOCList, String outputDir) {
        this.srcTOCList = srcTOCList;
        this.outputDir = outputDir;
    }

    public void write() throws IOException {
        saveTablesOfContentsTo(srcTOCList, new File(outputDir));
    }

    private void saveTablesOfContentsTo(TableOfContentListBuilder.TableOfContentList srcTOCList, File toDir) throws IOException {
        for (TableOfContent tableOfContent : srcTOCList.getTableOfContentList()) {
            File toSubDir = new File(toDir, tableOfContent.getBookParentName());
            FileUtils.makeDirIfDoesNotExist(toSubDir);
            File tableOfContentDestFile = new File(toSubDir, tableOfContent.getBookName() + ".txt");
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(tableOfContentDestFile)));
                saveTablesOfContentsTo(tableOfContent, pw);
            } finally {
                IOUtils.closeQuietly(pw);
            }
        }
    }

    private void saveTablesOfContentsTo(TableOfContent toc, PrintWriter pw) throws IOException {
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
                    if (titleOrder < 10) {
                        pw.println("0" + titleOrder + toci.getArticleTitle());
                    } else {
                        pw.println(titleOrder + toci.getArticleTitle());
                    }
                }
            } else {
                for (int i = 0; i < tociList.size(); i++) {
                    toci = tociList.get(i);
                    int titleOrder = toci.getOrder();
                    //目录一般是用空格隔开
                    if (titleOrder < 10) {
                        pw.println("00" + titleOrder + toci.getArticleTitle());
                    } else if (titleOrder < 100) {
                        pw.println("0" + titleOrder + toci.getArticleTitle());
                    } else {
                        pw.println(titleOrder + toci.getArticleTitle());
                    }
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
