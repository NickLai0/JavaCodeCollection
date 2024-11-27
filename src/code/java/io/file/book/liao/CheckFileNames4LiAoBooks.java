package code.java.io.file.book.liao;

import java.io.File;
import java.util.List;
import java.util.Set;

import static code.java.io.file.book.liao.data.LiAoBookStore.getLADQJBookFiles;
import static code.java.io.file.book.liao.data.LiAoBookStore.newLiaoBookNamesSet;
import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

/*
 * 校验李敖大全集5.0目录所有书名是否和目录里的163本书一致
 * 目录和文件夹相互校验后，发现都是163本书，无误。
 */
public class CheckFileNames4LiAoBooks {

    public static void main(String[] args) {
        checkLiAoBooks();
    }

    //根据李敖大全集5.0目录来检查李敖的书，将存在和不存在的书名都输出。
    private static void checkLiAoBooks() {
        Set<String> liaoBookNamesSet = newLiaoBookNamesSet();
        List<File> allBookFiles = getLADQJBookFiles();
        print("检查163本书目录中，文件夹下是否缺少-----------------");
        checkIfLackSomeBooks(liaoBookNamesSet, allBookFiles);
        println();
        println();
        print("检查文件夹下的书本，目录中是否没包含-----------------");
        checkIfLackSomeContent(liaoBookNamesSet, allBookFiles);
    }

    private static void checkIfLackSomeContent(Set<String> liaoBookNamesSet, List<File> allBookFiles) {
        for (File bookFile : allBookFiles) {
            if (!hasBook(liaoBookNamesSet, bookFile)) {
                println("目录中没有，但是文件夹有的书：" + bookFile);
            }
        }
    }

    private static void checkIfLackSomeBooks(Set<String> liaoBookNamesSet, List<File> allBookFiles) {
        for (String bookName : liaoBookNamesSet) {
            boolean found = false;
            for (File bookFile : allBookFiles) {
                if (bookFile.getName().equals(bookName + ".txt")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                println("Didn't find the book name:" + bookName);
            }
        }
    }

    private static boolean hasBook(Set<String> liaoBookNamesSet, File realBookFile) {
        String bookName = realBookFile.getName()
                .replace(".txt", "");
        return liaoBookNamesSet.contains(bookName);
    }

}
