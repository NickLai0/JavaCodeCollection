package code.java.io.file;

import code.java.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static code.java.utils.LU.println;

/**
 * 用李敖书籍目录的1-163的顺序，
 * 给书籍分布在17个目录下的每个文件复制
 * 到另外目录下，然后按从01-xx排序。
 * 如“01自传回忆类”目录下有八个文件，
 * 则从01-08排序，其它以此类推。
 * <p>
 * 复制的好处是不影响原始文件。
 */
public class CopyLiAoBooksToAnotherDirAndSortThem {

    public static void main(String[] args) throws IOException {
        List<String> liaoBookNamesList = LiAoBookStore.newLiaoBookNamesList();
        addSuffixForBookName(liaoBookNamesList, ".txt");

        //子目录对应书本排序信息列表
        File[] bookSubDirs = LiAoBookStore.getLiAoBooksRootDir().listFiles();
        HashMap<File, List<BookSortInfo>> subDirBookSortInfos = new HashMap<>();

        //组织子目录对应书本排序信息列表
        organizeSubDirBookSortInfos(subDirBookSortInfos, bookSubDirs, liaoBookNamesList);

        println("子目录下书本名称对应李敖大全集5.0的目录索引开始------------------------");
        printSubDirBookSortInfos(subDirBookSortInfos);
        println("子目录下书本名称对应李敖大全集5.0的目录索引结束------------------------");

        File newBookDir = new File(LiAoBookStore.getLiAoBooksRootDir() + "(书名按顺序排列)");
        FileUtils.makeDirIfDoesNotExist(newBookDir);
        //将原目录下的书本复制到目标目录，且目录名在原有基础上(的前面)加序号
        println("复制李敖大全集5.0目录下所有书本开始------------------------");
        copyBookFilesAndRenameThemByOrder(newBookDir, subDirBookSortInfos);
        println("复制李敖大全集5.0目录下所有书本结束------------------------");
    }

    //打印子目录信息
    private static void printSubDirBookSortInfos(HashMap<File, List<BookSortInfo>> subDirBookSortInfos) {
        Set<Map.Entry<File, List<BookSortInfo>>> entries = subDirBookSortInfos.entrySet();
        for (Map.Entry<File, List<BookSortInfo>> entry : entries) {
            File bookDir = entry.getKey();
            println(bookDir.getName());
            for (BookSortInfo bsi : entry.getValue()) {
                println("   " + bsi.bookFile.getName() + ", index=" + bsi.indexFromTableOfContent);
            }
        }
    }

    //将原目录下的书本复制到目标目录，且目录名在原有基础上(的前面)加序号
    private static void copyBookFilesAndRenameThemByOrder(
            File newBookRootDir,
            HashMap<File, List<BookSortInfo>> subDirBookSortInfos
    ) throws IOException {

        for (Map.Entry<File, List<BookSortInfo>> entry : subDirBookSortInfos.entrySet()) {
            File subDir = entry.getKey();
            File newBookDestSubDir = new File(newBookRootDir, subDir.getName());
            FileUtils.makeDirIfDoesNotExist(newBookDestSubDir);

            List<BookSortInfo> bsiList = new ArrayList<>(entry.getValue());

            for (int bookNameIndex = 1; bsiList.size() > 0; bookNameIndex++) {
                int index = findSmallestIndex(bsiList);
                BookSortInfo bsi = bsiList.remove(index);
                File srcFile = bsi.bookFile;
                File destFile = null;

                //目标文件名增加01——xx这样的序号
                if (bookNameIndex < 10) {
                    destFile = new File(newBookDestSubDir, "0" + bookNameIndex + srcFile.getName());
                } else {
                    destFile = new File(newBookDestSubDir, bookNameIndex + srcFile.getName());
                }
                FileUtils.copyFile(srcFile, destFile);
            }

        }

    }

    private static int findSmallestIndex(List<BookSortInfo> bsiList) {
        int smallestIndex = 0;
        if (bsiList.size() > 1) {
            BookSortInfo smallestBSI = bsiList.get(0);
            for (int i = 1; i < bsiList.size(); i++) {
                BookSortInfo bsi = bsiList.get(i);
                if (smallestBSI.indexFromTableOfContent > bsi.indexFromTableOfContent) {
                    //记录索引最小的BookSortInfo和对应的i
                    smallestBSI = bsi;
                    smallestIndex = i;
                }
            }
        }
        return smallestIndex;
    }

    //组织子目录对应书本排序信息列表
    private static void organizeSubDirBookSortInfos(HashMap<File, List<BookSortInfo>> subDirBookSortInfos, File[] bookSubDirs, List<String> liaoBookNamesList) {
        for (File bookSubDir : bookSubDirs) {//遍历书本子目录列表
            List<BookSortInfo> bookSortInfos = subDirBookSortInfos.get(bookSubDir);

            if (bookSortInfos == null) {
                bookSortInfos = new ArrayList<BookSortInfo>();
                subDirBookSortInfos.put(bookSubDir, bookSortInfos);
            }

            for (File bookFile : bookSubDir.listFiles()) {//遍历子目录下书本文件列表
                //根据书本名找出书本目录里面的索引
                int bookIndex = liaoBookNamesList.indexOf(bookFile.getName());
                if (bookIndex < 0) {
                    System.out.println("Din't find book name from table of content: " + bookFile.getName());
                    continue;
                }
                //保存找到的书本排序信息
                bookSortInfos.add(newBookSortInfo(bookFile, bookIndex));
            }
        }
    }

    private static BookSortInfo newBookSortInfo(File bookFile, int bookIndex) {
        BookSortInfo bookSortInfo = new BookSortInfo();
        bookSortInfo.bookFile = bookFile;
        bookSortInfo.indexFromTableOfContent = bookIndex;
        return bookSortInfo;
    }

    private static void addSuffixForBookName(List<String> liaoBookNamesList, String suffix) {
        for (int i = 0, size = liaoBookNamesList.size(); i < size; i++) {
            String bookName = liaoBookNamesList.get(i);
            String bookFileName = bookName + suffix;
            liaoBookNamesList.set(i, bookFileName);
        }
    }

    //书本排序信息
    static class BookSortInfo {
        File bookFile;//书本文件
        int indexFromTableOfContent;//书本在目录的index位置

        @Override
        public String toString() {
            return "BookSortInfo{" +
                    "bookFile=" + bookFile +
                    ", indexFromTableOfContent=" + indexFromTableOfContent +
                    '}';
        }
    }

}
