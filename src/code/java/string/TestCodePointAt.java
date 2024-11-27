package code.java.string;

public class TestCodePointAt {
    public static void main(String[] args) {
        //打印ASCII码在Unicode中的值
        String str = "Hello, World!";
        int codePoint = str.codePointAt(4);
        System.out.println("The Unicode code point at index 4 for \"" + str + "\" is: " + codePoint);

        //打印中文在Unicode中的值
        str = "你好，世界！";
        codePoint = str.codePointAt(0);
        System.out.println("The Unicode code point at index 0 for \"" + str + "\" is: " + codePoint);

        /*
        在Unicode编码中，像"𝄞"这样的字符由代理对（Surrogate Pair）表示。
        其取值超过两个字节：：0x010000——10FFFF（总共可表示是FFFFF个代理对字符）
         */
        str = "𝄞";
        codePoint = str.codePointAt(0);
        System.out.println("the Unicode code value of 𝄞（Surrogate Pair） character is: " + codePoint);
    }
}
