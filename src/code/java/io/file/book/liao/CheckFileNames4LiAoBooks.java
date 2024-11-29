package code.java.io.file.book.liao;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static code.java.io.file.book.liao.data.LiAoBookStore.getLADQJBookWithoHTMLFiles;
import static code.java.io.file.book.liao.data.LiAoBookStore.newLiaoBookNamesSet;
import static code.java.utils.LU.println;

/*
 * 校验李敖大全集5.0目录所有书名是否和目录里的163本书一致
 * 目录和文件夹相互校验后，发现都是163本书，无误。
 */
public class CheckFileNames4LiAoBooks {

    private static List<File> allBookFiles = getLADQJBookWithoHTMLFiles();

    private static Set<String> liaoBookNamesSet = newLiaoBookNamesSet();

    public static void main(String[] args) {
        println("检查目录在文件夹下的书本-----------------");
        checkIfLackSomeBooks();
        println("目录中" + liaoBookNamesSet.size() + "本书检查完毕。");

        println("检查文件夹在目录的书本-----------------");
        checkIfLackSomeContent();
        println("文件夹下" + getLADQJBookWithoHTMLFiles().size() + "本书检查完毕。");
    }

    //遍历目录的书本名称，检查文件夹下是否有该书
    private static void checkIfLackSomeContent() {
        for (String bookName : liaoBookNamesSet) {
            if (!findInBookDir(bookName)) {
                println("目录中不包含的书：" + bookName);
            }
        }
    }

    //遍历文件夹下所有书，检查目录里面是否存在
    private static boolean findInBookDir(String bookName) {
        for (File bookFile : allBookFiles) {
            if (isBookNameAndFilenameTheSame(bookName, bookFile.getName())) {
                return true;
            }
        }
        return false;
    }

    private static void checkIfLackSomeBooks() {
        for (File file : allBookFiles) {
            boolean found = isBookNameAndFilenameTheSame(file.getName());
            if (!found) {
                println("Couldn't find the book from table of content: " + file.getName());
            }
        }
    }

    private static boolean isBookNameAndFilenameTheSame(String bookFileName) {
        for (String bookName : liaoBookNamesSet) {
            if (
                    Pattern.compile(".*(" + bookName + ".txt)$")
                            .matcher(bookFileName)
                            .matches()
            ) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBookNameAndFilenameTheSame(String bookName, String bookFileName) {
        return Pattern.compile(".*(" + bookName + ".txt)$")
                .matcher(bookFileName)
                .matches();
    }

}
