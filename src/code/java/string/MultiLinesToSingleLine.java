package code.java.string;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static code.java.utils.LU.printSeparateLine;
import static code.java.utils.LU.println;

public class MultiLinesToSingleLine {

    private static final String QUIT_COMMAND = "#quit#";

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        println("请输入多行转一行内容（" + QUIT_COMMAND + "命令退出）：");
        String line;
        while ((line = br.readLine()) != null && line.length() > 0) {
            line = line.trim().replace(" ","");
            if (QUIT_COMMAND.equals(line)) {
                break;
            }
            if (line.length() > 0) {
                sb.append(line);
            }
        }
        printSeparateLine();
        println("结果：");
        println(sb.toString());
    }

}
