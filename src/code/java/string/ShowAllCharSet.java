package code.java.string;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.SortedMap;

import static code.java.utils.LU.println;

public class ShowAllCharSet {
    public static void main(String[] args) {
        println("available sharsets：");
        SortedMap<String, Charset> map = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : map.entrySet()) {
            //打印稍微看了一下，基本上key和value是用一样的
//            println("\t" + entry.getKey() + ": " + entry.getValue());
            println("\t" + entry.getValue());
        }
    }
}
