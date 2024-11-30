package code.java.io.file.book.liao.utils;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.data.BookTableOfContentAndBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ArticleListWriter {

    private final BookSeperatedListBuilder.BookSeperatedList srcTOCList;

    private final String storeDir;

    public ArticleListWriter(BookSeperatedListBuilder.BookSeperatedList srcTOCList, String storeDir) {
        this.srcTOCList = srcTOCList;
        this.storeDir = storeDir;
    }

    public void write() throws FileNotFoundException {
        saveArticlesTo(srcTOCList, new File(storeDir));
    }

    private StringBuffer sbArticleBuilder = new StringBuffer();

    private void saveArticlesTo(BookSeperatedListBuilder.BookSeperatedList srcTOCList, File file) throws FileNotFoundException {
        /*for (BookTableOfContentAndBody toc : srcTOCList.getTableOfContentList()) {
            BufferedReader br = new BufferedReader(new FileReader(toc.getTableOfContentPath()));

        }*/
    }

}
