package code.java.io.file.book.liao;

import java.io.*;

import static code.java.utils.LU.println;

/**
 * 检查李敖书本的文件名是否书本第一句话（即标题）
 * 对应，不对应的要手动纠正。
 *
 * 输出如下:
 *    文件名和書名不同：filename=为历史拨云bookName=为历史拔云
 *    文件名和書名不同：filename=大江大海骗了你bookName=大江大海骗了你——李敖秘密谈话录
 *
 * 按：文件里面的书名已改正为“为历史拨云”和大江大海骗了你——李敖秘密谈话录
 *
 */
public class CheckLiAoBooksAndFilenameIfTheyAreTheSame {
    public static void main(String[] args) throws IOException {
        String dir = "D:\\code\\java\\JavaCodeCollection\\temp\\李敖大全集5.0(书名按顺序排列)(去除html，仅有文字内容)";
        File liAoBooksRootDir = new File(dir);
        for (File liAoBooksSubDir : liAoBooksRootDir.listFiles()) {
            for (File liAoBookFile : liAoBooksSubDir.listFiles()) {
                BufferedReader br = new BufferedReader(new FileReader(liAoBookFile));
                String bookName = br.readLine().trim();
                String filename = liAoBookFile.getName()
                        .replace(".txt", "")//去後綴
                        .substring(2);//去前面序號
                if(!filename.equals(bookName)) {
                    println("文件名和書名不同：filename="+filename+"， bookName="+bookName);
                }
            }
        }
    }
}
