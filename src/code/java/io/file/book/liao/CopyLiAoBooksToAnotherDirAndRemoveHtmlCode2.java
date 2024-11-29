package code.java.io.file.book.liao;

import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;

import static code.java.utils.LU.*;


//将李敖163本书的内容去掉html标签
//然后将存内容保持到另外目录
//最后纠正一些不正确的书本题目和书本名称
public class CopyLiAoBooksToAnotherDirAndRemoveHtmlCode2 {

    public static void main(String[] args) throws IOException {
        File rootDir = LiAoBookStore.getLiAoBooksSortedRootDir();
        File destRootDir = new File(rootDir.getParent(), rootDir.getName() + "(去除html，仅有文字内容)");
        FileUtils.makeDirIfDoesNotExist(destRootDir);
        println("复制含html的书本内容后，去除html，并统一文件名和书本标题名后存放");
        println("Copy books(including HTML) from: " + rootDir);
        println("Copy books(Removed HTML) To: " + destRootDir);
        for (File srcSubDir : rootDir.listFiles()) {
            File destSubDir = new File(destRootDir, srcSubDir.getName());
            FileUtils.makeDirIfDoesNotExist(destSubDir);

            for (File srcFile : srcSubDir.listFiles()) {
                String htmlText = FileUtils.fileToString(srcFile);
                String bookText = htmlTextToBookText(htmlText);
                String fileName = srcFile.getName();
                String[] bookTextAndFileName = {bookText, fileName};
                fixBookTextAndFileName(bookTextAndFileName);
                saveBookText(bookTextAndFileName[0], destSubDir, bookTextAndFileName[1]);
            }
        }
        println("复制完成，请查看：" + destRootDir);
    }

    private static void fixBookTextAndFileName(String[] bookTextAndFileName) throws IOException {
        String fileName = bookTextAndFileName[1];
        if (fileName.contains("为历史拨云")) {
            //“为历史拨云”是书名错而文件名对，所以纠正书本里的标题
            bookTextAndFileName[0] = bookTextAndFileName[0].replace("为历史拔云", "为历史拨云");
            println("纠正文件里面的书本标题：为历史拔云  -> 为历史拨云");
            printSeparateLine();
        } else if (fileName.endsWith("大江大海骗了你.txt")) {
            // “大江大海骗了”是文件名错而书名对,所以纠正文件名
            bookTextAndFileName[1] = fileName.replace(
                    "大江大海骗了你", "大江大海骗了你——李敖秘密谈话录"
            );
            println("纠正文间名：大江大海骗了你 -> 大江大海骗了你——李敖秘密谈话录");
            printSeparateLine();
        } else if (fileName.contains("中国性研究")) {
            println("《中国性研究》和其他书的目录规则有区别，改为统一的书名-》目录-》目录列表结构");
            doubleBookNamesToOneBookNameWith目录(bookTextAndFileName,2,"中国性研究\n目录");
            println("修改后书本的前50个字符：" + bookTextAndFileName[0].substring(0, 50));
            printSeparateLine();
        }else if (fileName.contains("蒋介石评传")) {
            println("《蒋介石评传》和其他书的目录规则有区别，改为统一的书名-》目录-》目录列表结构");
            doubleBookNamesToOneBookNameWith目录(bookTextAndFileName,2,"蒋介石评传\n目录");
            println("修改后书本的前50个字符：" + bookTextAndFileName[0].substring(0, 50));
            printSeparateLine();
        }
    }

    /**
     * 《中国性研究》的排版：
     * 中国性研究
     * 中国性研究
     * 目录列表
     * ....
     * <p>
     * TMD！ <蒋介石评传>也是下面的这种鬼排版:
     * 蒋介石评传
     * 蒋介石评传
     * 《蒋介石评传》序（汪荣祖）...
     * <p>
     * 将上面的这种排版前两句统一改为书本名->目录->目录列表结构
     */
    private static void doubleBookNamesToOneBookNameWith目录(String[] bookTextAndFileName, int removeLines, String replaceRemovedStr) throws IOException {
        String bookText = bookTextAndFileName[0];
        BufferedReader br = new BufferedReader(new StringReader(bookText));
        int length = 0;
        for (int i = 0; i < removeLines; i++) {
            length += br.readLine().length();
        }
        bookTextAndFileName[0] = replaceRemovedStr+ bookText.substring(length);

    }

    private static void saveBookText(String bookText, File destDir, String destFileName) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bookText.getBytes());
        IOUtils.copy(bais, new FileOutputStream(new File(destDir, destFileName)));
    }

    /**
     * 将如下样式html包含起来的书籍内容给抠出仅书籍内容字符串。
     * ......（其它html标签略）
     * "Array.from(document.querySelectorAll('#sid
     * link.setAttribu te('tabIndex', sidebar
     * });";
     * ......(书籍内容略)//其实就是抠出这部分
     * "window.playground_copyable = true;";
     *
     * @param htmlText 上面样式的html标签包含这文本的文本
     * @return //纯书籍内容
     */
    private static String htmlTextToBookText(String htmlText) {
        String rawTextStartHtmlStr = "Array.from(document.querySelectorAll('#sidebar a')).forEach(function(link) {\n" + "                        link.setAttribu te('tabIndex', sidebar === 'visible' ? 0 : -1);\n" + "                    });";
        String rawTextEndHtmlStr = "window.playground_copyable = true;";

        int textStart = htmlText.indexOf(rawTextStartHtmlStr) + rawTextStartHtmlStr.length();
        int textEnd = htmlText.indexOf(rawTextEndHtmlStr);
        String bookText = null;
        if (textEnd > 0) {
            bookText = htmlText.substring(textStart, textEnd);
        } else {
            bookText = htmlText.substring(textStart);
        }
        return bookText.trim();
    }

}
