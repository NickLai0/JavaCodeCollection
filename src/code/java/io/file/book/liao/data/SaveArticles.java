package code.java.io.file.book.liao.data;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.utils.ArticleListWriter;
import code.java.io.file.utils.FilesLastLinesPrinter;
import code.java.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static code.java.utils.LU.println;

/**
 * 将李敖大全集5.0纯文本的文章全部切割出来
 * 并按照所属分类目录排序存放。
 */
public class SaveArticles {

    public static void main(String[] args) throws IOException {
        File liaoBooksRootDir = LiAoBookStore.getLiAoBooksWithoutHTMLRootDir();
        BookSeperatedListBuilder.BookSeperatedList tableOfContentList = new BookSeperatedListBuilder().setBookSrcDir(liaoBooksRootDir.getAbsolutePath()).build();
        File liaoBooksTablesOfContentsRootDir = getTablesOfContentDir();
        println("将李敖大全集5.0纯文本的文章全部切割出来,并按照所属分类目录排序存放。");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + liaoBooksTablesOfContentsRootDir);
        ArticleListWriter tableWriter = new ArticleListWriter(tableOfContentList, liaoBooksTablesOfContentsRootDir.getAbsolutePath());
        tableWriter.write();
        println("Copy finished.");
        //showAFewLines4EachTableOfContent(liaoBooksTablesOfContentsRootDir);
        checkExceptedTableOfContent(tableOfContentList);
    }

    private static final int EXCEPTED_NUMBER = 1;

    private static void checkExceptedTableOfContent(BookSeperatedListBuilder.BookSeperatedList tableOfContentList) {
        List<BookTableOfContentAndBody> tocList = tableOfContentList.getTableOfContentList();
        for (BookTableOfContentAndBody toc : tocList) {
            if (toc.getTableOfContentItemList().size() <= EXCEPTED_NUMBER) {
                println("《" + toc.getBookName() + "》 的目录小于" + EXCEPTED_NUMBER + ", 怀疑有异常。");
            }
        }
    }

    private static void showAFewLines4EachTableOfContent(File liaoBooksTablesOfContentsRootDir) throws IOException {
        println("--------------------------------------------------------");
        new FilesLastLinesPrinter(liaoBooksTablesOfContentsRootDir.getAbsolutePath(), 2).print();
    }

    private static File getTablesOfContentDir() {
        File liaoBooksTablesOfContentsRootDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(每本书的文章)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }


}
