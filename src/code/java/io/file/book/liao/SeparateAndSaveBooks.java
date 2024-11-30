package code.java.io.file.book.liao;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.utils.FilesLastLinesPrinter;
import code.java.io.file.utils.BookSeperatedListWriter;
import code.java.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static code.java.utils.LU.println;

/**
 * 从李敖大全集目录下复制每本书的目录出来格式化后，
 * 输出到目标文件，每篇文章则切割保存
 */
public class SeparateAndSaveBooks {

    public static void main(String[] args) throws IOException {
        File liaoBooksRootDir = LiAoBookStore.getLiAoBooksWithoutHTMLRootDir();
        BookSeperatedListBuilder.BookSeperatedList bsl = new BookSeperatedListBuilder()
                .setBookSrcDir(liaoBooksRootDir.getAbsolutePath())
                .build();
        File tacsDir = getTablesAndContentSeparatedDir();
        println("切割李敖大全集的书为：目录表（格式化的）和整书内容（未格式化）两部分。");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + tacsDir);
        BookSeperatedListWriter writer = new BookSeperatedListWriter(bsl, tacsDir.getAbsolutePath());
        writer.write();
        println("Copy finished.");
        checkExceptedTableOfContent(bsl);
    }

    private static final int EXCEPTED_NUMBER = 10;

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

    private static File getTablesAndContentSeparatedDir() {
        File liaoBooksTablesOfContentsRootDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(目录和内容分离)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }


}
