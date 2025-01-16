package code.java.data;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.io.IOException;
import java.util.Properties;

public class JDBCDriverInfo {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public JDBCDriverInfo() {
    }

    public JDBCDriverInfo(String drivers, String url, String username, String password) {
        this.driverClassName = drivers;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //静态工厂方法
    public static JDBCDriverInfo create4FKJJY() throws IOException {
        // 使用Properties类来加载属性文件
        Properties props = FKJJYUtils.loadDatabaseProperties();
        return new JDBCDriverInfo(
                props.getProperty("jdbc.drivers")
                , props.getProperty("jdbc.url")
                , props.getProperty("jdbc.username")
                , props.getProperty("jdbc.password")
        );
    }
}
