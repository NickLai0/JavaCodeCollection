package code.java.data;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.PropertiesUtils;

import java.io.IOException;
import java.util.Properties;

public class JDBCDriverInfo {

    protected String driverClassName;
    protected String url;
    protected String username;
    protected String password;

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
        Properties props = PropertiesUtils.loadProperty(FKJJYUtils.getInitFileTestSelect());
        return create(props);
    }

    //c
    public static JDBCDriverInfo create4informationSchema() throws IOException {
        Properties props = PropertiesUtils.loadProperty(FKJJYUtils.getInitFileInformationSchema());
        return create(props);
    }

    private static JDBCDriverInfo create(Properties props) {
        return new JDBCDriverInfo(
                props.getProperty("jdbc.drivers")
                , props.getProperty("jdbc.url")
                , props.getProperty("jdbc.username")
                , props.getProperty("jdbc.password")
        );
    }

}
