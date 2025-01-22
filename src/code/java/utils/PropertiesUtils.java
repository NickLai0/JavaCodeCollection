package code.java.utils;

import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtils {
    //加载测试数据库的ini配置文件
    public static java.util.Properties loadProperty(String path) throws IOException {
        java.util.Properties props = new java.util.Properties();
        FileInputStream in = new FileInputStream(path);
        props.load(in);
        in.close();
        return props;
    }
}
