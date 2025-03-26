package code.java.string;

import code.java.utils.ProjectFileUtils;

import java.io.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

public class RemoveSpecificChar4OneString {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        println("请输入多行字符串（#quit#）为退出标志：");
        StringBuffer sb = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            if ("#quit#".equals(str)) {
                break;
            }
            sb.append(str);
        }
        print("请输入要去除的符号：");
        String oldReplace = br.readLine();
        String result = sb.toString().replace(oldReplace, "");
        print("请输入要保存的文件名：");
        String fileName = br.readLine();
        File f = new File(ProjectFileUtils.getTempDir(), fileName + ".txt");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(result.getBytes());
        fos.close();
        println("结果已经输出在工程目录的temp目录下");
    }
}
