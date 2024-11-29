package code.java.io.file.book.liao;

import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.regex.Pattern;

import static code.java.utils.LU.println;

/**
 * 检查李敖书本的文件名是否书本第一句话（即标题）
 * 对应，不对应的要手动纠正。
 * <p>
 * 输出如下:
 * 文件名和書名不同：filename=为历史拨云，bookName=为历史拔云
 * 文件名和書名不同：filename=大江大海骗了，你bookName=大江大海骗了你——李敖秘密谈话录
 * <p>
 * 按：“为历史拨云”是书名错而文件名对；“大江大海骗了”是文件名错而书名对。
 *
 * @Link 逻辑整合到了CopyLiAoBooksToAnotherDirAndRemoveHtmlCode2
 */
@Deprecated
public class MakeWrongFilenamesAndBookNamesCorrect {
    public static void main(String[] args) throws IOException {
        String dir = "D:\\code\\java\\JavaCodeCollection\\temp\\李敖大全集5.0(书名按顺序排列)(去除html，仅有文字内容)";
        File rootDir = new File(dir);
        for (File subDir : rootDir.listFiles()) {
            for (File bookFile : subDir.listFiles()) {
                BufferedReader br = new BufferedReader(new FileReader(bookFile));
                String bookName = br.readLine().trim();
                IOUtils.closeQuietly(br);
                String fileName = bookFile.getName();
                Pattern p = Pattern.compile("\\d{2}" + bookName + "(.txt)$");
                if (!p.matcher(fileName).matches()) {
                    println("文件名和書名不同：filename=" + fileName + "， bookName=" + bookName);
                    /**
                     * 输出如下:
                     *    文件名和書名不同：filename=为历史拨云，bookName=为历史拔云
                     *    文件名和書名不同：filename=大江大海骗了，你bookName=大江大海骗了你——李敖秘密谈话录
                     * “为历史拨云”是书名错而文件名对；“大江大海骗了”是文件名错而书名对。
                     */
                    //下面代码写死了。。。
                    if (fileName.contains("为历史拨云")) {
                        //“为历史拨云”是书名错而文件名对，所以纠正书本里的标题
                        String s = FileUtils.fileToString(bookFile);
                        s = s.replace("为历史拔云", "为历史拨云");
                        ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
                        IOUtils.copy(is, new FileOutputStream(bookFile));
                        println("书标题纠正：为历史拔云 -> 为历史拨云");
                    } else if (fileName.endsWith("大江大海骗了你.txt")) {
                       // “大江大海骗了”是文件名错而书名对,所以纠正文件名
                        String correctFilename = fileName.replace("大江大海骗了你", "大江大海骗了你——李敖秘密谈话录");
                        if (bookFile.renameTo(new File(bookFile.getParent(), correctFilename))) {
                            println("文件名纠正：" + fileName + " -> " + correctFilename);
                        }
                    }

                    println("-----------------------------------------------------------------");
                }
            }
        }
    }
}
