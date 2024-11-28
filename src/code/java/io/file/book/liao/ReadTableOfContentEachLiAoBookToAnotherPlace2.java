package code.java.io.file.book.liao;

import code.java.io.file.book.liao.builder.TableOfContentListBuilder;
import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.io.file.utils.FilesLastLinesPrinter;
import code.java.utils.FileUtils;
import code.java.utils.ProjectFileUtils;

import java.io.File;
import java.io.IOException;

import static code.java.utils.LU.println;

/**
 * 从李敖大全集目录下复制每本书的目录出来格式化后，输出到目标文件
 * 这个比上一个同名类版本来说，职责分离更清晰
 */
public class ReadTableOfContentEachLiAoBookToAnotherPlace2 {

    public static void main(String[] args) throws IOException {
        File liaoBooksRootDir = new File(ProjectFileUtils.getProjectRootDir(), "temp/李敖大全集5.0(书名按顺序排列)(去除html，仅有文字内容)");
        TableOfContentListBuilder.TableOfContentList tableOfContentList = new TableOfContentListBuilder().setBookDir(liaoBooksRootDir.getAbsolutePath()).build();
        File liaoBooksTablesOfContentsRootDir = getTablesOfContentDir();
        println("从李敖大全集目录下复制每本书的目录出来格式化后，输出到目标文件");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + liaoBooksTablesOfContentsRootDir);
        TableOfContentListWriter tableWriter = new TableOfContentListWriter(tableOfContentList, liaoBooksRootDir.getAbsolutePath());
        tableWriter.write();
        println("Copy finished.");

        //showLastThreeLinesOfEveryBook();
        println("--------------------------------------------------------");
        new FilesLastLinesPrinter(
                liaoBooksTablesOfContentsRootDir.getAbsolutePath(),
                2)
                .print();
    }

    private static File getTablesOfContentDir() {
        File liaoBooksTablesOfContentsRootDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(每本书的目录)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }


}
