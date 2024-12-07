package code.java.io.file.book.liao;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.utils.FilesLastLinesPrinter;
import code.java.io.file.utils.BookSeperatedListWriter;
import code.java.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

/**
 * 从李敖大全集目录下复制每本书的目录出来格式化后，
 * 输出到目标文件，每篇文章则切割保存
 */
public class SeparateAndSaveBooks {

    public static void main(String[] args) throws IOException {
        /*File liaoBooksRootDir = LiAoBookStore.getLiAoBooksWithoutHTMLRootDir();
        File tacsDir = getTablesAndContentSeparatedDir();
        println("切割李敖大全集的书为：切割出目录表和每篇文章来保存。");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + tacsDir);*/
        File[] dirs = inputSrcAndDestDirs();
        File srcDir = dirs[0];
        File destDir = dirs[1];
        BookSeperatedListBuilder.BookSeperatedList bsl =
                new BookSeperatedListBuilder()
                        .setBookSrcDir(srcDir.getAbsolutePath())
                        .build();
        BookSeperatedListWriter writer = new BookSeperatedListWriter(bsl, destDir.getAbsolutePath());
        writer.write();
        println("Copy finished.");
        checkExceptedTableOfContent(bsl);
    }

    /**
     * 获取书本原目录和目标输出目录。
     *
     * @return
     */
    private static File[] inputSrcAndDestDirs() throws IOException {
        File[] dirs = new File[2];
        println("切割李敖大全集的书为：切割出目录表和每篇文章来保存。");
        print("请输入整本书的源文件文目：");
        dirs[0] = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        print("请输入书本切割出目录和每篇文章后要保持的目录：");
        dirs[1] = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        return dirs;
    }

    private static final int EXCEPTED_NUMBER = 2;

    private static void checkExceptedTableOfContent(BookSeperatedListBuilder.BookSeperatedList tableOfContentList) {
        List<BookTableOfContentAndBody> tocList = tableOfContentList.getTableOfContentList();
        for (BookTableOfContentAndBody toc : tocList) {
            if (toc.getTableOfContentItemList().size() < EXCEPTED_NUMBER) {
                println("《" + toc.getBookName() + "》 的目录小于" + EXCEPTED_NUMBER + ", 怀疑有异常。");
            }
        }
    }

    private static void showAFewLines4EachTableOfContent(File liaoBooksTablesOfContentsRootDir) throws IOException {
        println("--------------------------------------------------------");
        new FilesLastLinesPrinter(liaoBooksTablesOfContentsRootDir.getAbsolutePath(), 2).print();
    }

    private static File getTablesAndContentSeparatedDir() {
        File liaoBooksTablesOfContentsRootDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(每本书的目录和每篇文章分离)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }


}
