package code.java.io.file.book.liao;

import code.java.io.file.book.liao.data.LiAoBookStore;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;

import static code.java.utils.LU.println;

//将李敖163本书的内容去掉html标签，然后将存内容保持到另外目录
public class CopyLiAoBooksToAnotherDirAndRemoveHtmlCode {

    public static void main(String[] args) throws IOException {
        File rootDir = LiAoBookStore.getLiAoBooksSortedRootDir();
        File destRootDir = new File(rootDir.getParent(), rootDir.getName() + "(去除html，仅有文字内容)");
        FileUtils.makeDirIfDoesNotExist(destRootDir);
        println("复制李敖的带html的书籍内容到内存后去掉html，仅留书籍内容，然后复制到：" + destRootDir);
        for (File srcSubDir : rootDir.listFiles()) {
            File destSubDir = new File(destRootDir, srcSubDir.getName());
            FileUtils.makeDirIfDoesNotExist(destSubDir);

            for (File srcFile : srcSubDir.listFiles()) {
                String htmlText = FileUtils.fileToString(srcFile);
                String bookText = htmlTextToBookText(htmlText);
                saveBookText(bookText, destSubDir, srcFile.getName());
            }
        }
        println("复制完成，请查看：" + destRootDir);
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
        String rawTextStartHtmlStr = "Array.from(document.querySelectorAll('#sidebar a')).forEach(function(link) {\n" +
                "                        link.setAttribu te('tabIndex', sidebar === 'visible' ? 0 : -1);\n" +
                "                    });";
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
