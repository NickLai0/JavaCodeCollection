package code.java.io.file.book.fkjjy.utils;

import code.java.data.JDBCDriverInfo;
import code.java.utils.ProjectFileUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FKJJYUtils {

    private static final String ROOT_DIR;
    private static final String SQL_DIR;
    private static final String INIT_FILE_TEST_SELECT;
    private static final String INIT_FILE_INFORMATION_SCHEMA;

    static {
        ROOT_DIR = ProjectFileUtils.getsDatabaseDir() + "\\fkjjy";
        SQL_DIR = ROOT_DIR + "\\sql";
        INIT_FILE_TEST_SELECT = SQL_DIR + "\\conn_test_select.ini";
        INIT_FILE_INFORMATION_SCHEMA = SQL_DIR + "\\conn_information_schema.ini";
    }

    public static String getRootDir() {
        return ROOT_DIR;
    }

    public static String getSqlDir() {
        return SQL_DIR;
    }

    public static String getInitFileTestSelect() {
        return INIT_FILE_TEST_SELECT;
    }

    public static String getInitFileInformationSchema() {
        return INIT_FILE_INFORMATION_SCHEMA;
    }

    private static JDBCDriverInfo sJdbcDriverInfo;
    private static JDBCDriverInfo sInformationSchemaDriverInfo;

    /**
     * 获取数据库driver的基础信息对象
     *
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static JDBCDriverInfo getJDBCDriverInfo() throws SQLException, IOException, ClassNotFoundException {
        if (sJdbcDriverInfo == null) {
            sJdbcDriverInfo = JDBCDriverInfo.create4FKJJY();
        }
        return sJdbcDriverInfo;
    }

    //获取系统数据库information_schema的driver信息
    public static JDBCDriverInfo getInformationSchemaDriverInfo() throws SQLException, IOException, ClassNotFoundException {
        if (sInformationSchemaDriverInfo == null) {
            sInformationSchemaDriverInfo = JDBCDriverInfo.create4informationSchema();
        }
        return sInformationSchemaDriverInfo;
    }

    /**
     * 获取疯狂Java讲义书籍对应的测试数据库的Connection。
     *
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection getSelectTestDBConnection() throws SQLException, IOException, ClassNotFoundException {
        return getConnection(getJDBCDriverInfo());
    }

    /**
     * 获取系统数据库的Connection。
     *
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Connection getInformationSchemaDBConnection() throws SQLException, IOException, ClassNotFoundException {
        return getConnection(getInformationSchemaDriverInfo());
    }

    private static Connection getConnection(JDBCDriverInfo info) throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName(info.getDriverClassName());
        // 取得数据库连接
        return DriverManager.getConnection(
                info.getUrl(),
                info.getUsername(),
                info.getPassword()
        );
    }

}
