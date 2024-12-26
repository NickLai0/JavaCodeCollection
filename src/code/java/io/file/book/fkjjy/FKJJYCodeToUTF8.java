package code.java.io.file.book.fkjjy;

import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

/**
 * 将源文件夹下的所有文件（含子目录下的文件），
 * 用GB2312编码读取出来后，再用UTF-8编码保存
 * 到目标目录，同时在"all files(system generated)"
 * 目录下也保持一份。
 * <p>
 * 因《疯狂Java讲义第4版》源代码+课件下的代码文件是用GB2312编码存放，
 * 但IntelliJ IDEA默认用UTF8，所以直接打开会乱码。
 * <p>
 * 解决方案：
 * 1、先用GB2312编码读取源代码文件，解析为Java的unicode字符串
 * 2、然后将其保存，由于默认使用UTF-8编码集，所以按照UTF-8编码集
 * 保存(用IntelliJ IDEA打开后就没有乱码了)。
 * <p>
 * D:\坚果云盘\notebook\软件研究\Java\books\疯狂Java讲义\《疯狂Java讲义第4版》高清PDF+源代码+课件\《疯狂Java讲义第4版》源代码+课件\codes\12
 * D:\code\java\JavaCodeCollection\temp\书本处理目标目录
 */
public class FKJJYCodeToUTF8 {

    private static File sGlobalDir;

    public static void main(String[] args) throws IOException {
        println("Source code file（.java） to UTF-8 encode.");
        print("Source directory(or file):");
        File srcDir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        print("dest directory:");
        File destDir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());

        sGlobalDir = new File(destDir, "all files(system generated)");
        FileUtils.makeDirIfDoesNotExist(sGlobalDir);

        FKJJYCodeToUTF8 f = new FKJJYCodeToUTF8();
        f.toUTF8ForJavaSourceCodeFilesRecursively(srcDir, destDir);
    }

    /**
     * 递归转txtFromDir目录下的.java文件为UTF-8编码集，
     * 保存到toDir目录下
     *
     * @param f
     * @param toDir
     * @throws IOException
     */
    private void toUTF8ForJavaSourceCodeFilesRecursively(File f, File toDir) throws IOException {
        if (f.isDirectory()) {
            //来源是目录
            File newDestDir = new File(toDir, f.getName());
            //在目标目录创建同名子目录
            newDestDir.mkdirs();
            for (File anotherF : f.listFiles()) {
                toUTF8ForJavaSourceCodeFilesRecursively(anotherF, newDestDir);
            }
        } else {
            if (f.getName().endsWith(".java")) {
                javaSourceCodeToUTF8(f, toDir);
            }
        }
    }

    /**
     * 1、将原.java文件字符串读出来，转为UTF8编码
     * 2、将转后字符串保存到目标文件夹下同名文件
     * 3、全局文件也存一份（用来减少复制效率）
     *
     * @param f
     * @param toDir
     */
    private void javaSourceCodeToUTF8(File f, File toDir) throws IOException {
        String utf8Str = fileToString(f);
        //先复制一份到目标目录
        IOUtils.copy(
                new StringReader(utf8Str),
                new FileOutputStream(new File(toDir, f.getName()))
        );
        //然后复制一份到全局目录
        IOUtils.copy(
                new StringReader(utf8Str),
                new FileOutputStream(new File(sGlobalDir, f.getName()))
        );
    }

    public String fileToString(File file) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) file.length());
        try {
            fis = new FileInputStream(file);
            IOUtils.copy(fis, baos);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return baos.toString("GB2312");
    }

}
