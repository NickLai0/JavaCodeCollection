package code.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class MD5Utils {
    public static String getFileMD5(String filePath) throws IOException, NoSuchAlgorithmException {
        // 获取文件输入流
        try (InputStream is = new FileInputStream(filePath)) {
            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // 使用 digest() 方法来计算输入流的 MD5 哈希值
            byte[] byteArray = new byte[1024];

            int len;

            while ((len = is.read(byteArray)) > 0) {
                digest.update(byteArray, 0, len);
            }

            // 获取字节数组，包含哈希值，然后转换成十六进制的字符串形式
            byte[] bytes = digest.digest();
            return bytesToHexString(bytes);
        }

    }
    /**
     * 完整的MD5编码过程
     * @param input 原始字符串
     * @return 32位小写MD5字符串
     * @throws RuntimeException 当MD5算法不可用时
     */
    public static String getMD5(String input) {
        // 参数校验
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        try {
            // 1. 获取MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2. 将字符串转换为字节数组（必须指定编码）
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

            // 3. 计算哈希值（digest方法会执行完整计算）
            byte[] hashBytes = md.digest(inputBytes);

            // 4. 将字节数组转换为十六进制表示
            return bytesToHexString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // 理论上所有Java实现都必须支持MD5
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    /**
     * 字节数组转十六进制字符串（优化版）
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        // 一个字节对应两个十六进制字符
        char[] hexChars = new char[bytes.length * 2];
        // 预定义十六进制字符
        final char[] hexArray = "0123456789abcdef".toCharArray();

        for (int i = 0; i < bytes.length; i++) {
            // 取字节的高4位
            int high = (bytes[i] & 0xF0) >>> 4;
            // 取字节的低4位
            int low = bytes[i] & 0x0F;
            // 转换为对应的十六进制字符
            hexChars[i * 2] = hexArray[high];
            hexChars[i * 2 + 1] = hexArray[low];
        }
        return new String(hexChars);
    }
}