package code.java.security;

import code.java.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

public class GenerateFileMD5 {
    public static void main(String[] args) {
        while (true) {
            try {
                print("input file path（input “quit” to end）：");
                String filePath = new BufferedReader(new InputStreamReader(System.in)).readLine();
                if ("quit".equals(filePath)) {
                    break;
                }
                println("MD5：" + getFileMD5(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileMD5(String filePath) {
        try {
            print("--------MD5Utils.getFileMD5(filePath)--------");
            return MD5Utils.getFileMD5(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}