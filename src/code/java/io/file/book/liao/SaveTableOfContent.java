package code.java.io.file.book.liao;

import code.java.io.file.book.liao.builder.TableOfContentListBuilder;
import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.io.file.book.liao.data.TableOfContent;
import code.java.io.file.utils.FilesLastLinesPrinter;
import code.java.io.file.utils.TableOfContentListWriter;
import code.java.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static code.java.utils.LU.println;

/**
 * 从李敖大全集目录下复制每本书的目录出来格式化后，
 * 输出到目标文件
 * 这个比ReadTableOfContentEachLiAoBookToAnotherPlace来说，
 * 职责分离更清晰
 */
public class SaveTableOfContent {

    public static void main(String[] args) throws IOException {
        File liaoBooksRootDir = LiAoBookStore.getLiAoBooksWithoutHTMLRootDir();
        TableOfContentListBuilder.TableOfContentList tableOfContentList = new TableOfContentListBuilder().setBookDir(liaoBooksRootDir.getAbsolutePath()).build();
        File liaoBooksTablesOfContentsRootDir = getTablesOfContentDir();
        println("从李敖大全集目录下复制每本书的目录出来格式化后，输出到目标文件");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + liaoBooksTablesOfContentsRootDir);
        TableOfContentListWriter tableWriter = new TableOfContentListWriter(tableOfContentList, liaoBooksTablesOfContentsRootDir.getAbsolutePath());
        tableWriter.write();
        println("Copy finished.");
        //showAFewLines4EachTableOfContent(liaoBooksTablesOfContentsRootDir);
        checkExceptedTableOfContent(tableOfContentList);
    }

    private static final int EXCEPTED_NUMBER = 1;

    private static void checkExceptedTableOfContent(TableOfContentListBuilder.TableOfContentList tableOfContentList) {
        List<TableOfContent> tocList = tableOfContentList.getTableOfContentList();
        for (TableOfContent toc : tocList) {
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
        File liaoBooksTablesOfContentsRootDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(每本书的目录)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }


}
