package code.java.io.file.book.fkjjy.utils;

import code.java.utils.ProjectFileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FKJJYUtils {

    private static final String ROOT_DIR;
    private static final String SQL_DIR;
    private static final String INIT_FILE_PATH;

    static {
        ROOT_DIR = ProjectFileUtils.getsDatabaseDir() + "\\fkjjy";
        SQL_DIR = ROOT_DIR + "\\sql";
        INIT_FILE_PATH = SQL_DIR + "\\conn.ini";
    }

    public static String getRootDir() {
        return ROOT_DIR;
    }

    public static String getSqlDir() {
        return SQL_DIR;
    }

    public static String getInitFilePath() {
        return INIT_FILE_PATH;
    }

    /**
     * 获取疯狂Java讲义书籍对应的测试数据库的Connection。
     *
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection getTestDBConnection() throws SQLException, IOException, ClassNotFoundException {
        // 通过加载conn.ini文件来获取数据库连接的详细信息
        Properties props = loadDatabaseProperties();
        String drivers = props.getProperty("jdbc.drivers");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        // 加载数据库驱动
        Class.forName(drivers);
        // 取得数据库连接
        return DriverManager.getConnection(url, username, password);
    }

    //加载测试数据库的ini配置文件
    public static Properties loadDatabaseProperties() throws IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(getInitFilePath());
        props.load(in);
        in.close();
        return props;
    }

}
