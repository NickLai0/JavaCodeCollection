package code.java.utils;

import code.java.database.select.ShowAllData;

import java.sql.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

public class JDBCUtils {

    public static void showTableData(String tableName) {
        try {
            new ShowAllData(tableName).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印查询的数据集的所有列名
     *
     * @param rs
     * @throws SQLException
     */
    public static void printColumnNames(ResultSet rs) throws SQLException {
        printColumnNames(rs.getMetaData(), "\t");
    }

    /**
     * 打印查询的数据集的所有列名
     */
    public static void printColumnNames(ResultSetMetaData rsmd) throws SQLException {
        printColumnNames(rsmd, "\t");
    }

    /**
     * 打印查询的数据集的所有列名
     */
    public static void printColumnNames(ResultSetMetaData rsmd, String separator) throws SQLException {
        for (int i = 0, size = rsmd.getColumnCount(); i < size; i++) {
            print(rsmd.getColumnName(i + 1));
            print(separator);
        }
        println();
    }

    /**
     * 执行conn连接的sql语句后，
     * close Statement对象，
     * 并返回boolean结果。
     *
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public static boolean executeSql(Connection conn, String sql) throws Exception {
        boolean hasResultSet;
        try ( // 获取数据库连接
              // 使用Connection来创建一个Statement对象
              Statement stmt = conn.createStatement()) {
            // 执行SQL,返回boolean值表示是否包含ResultSet
            hasResultSet = stmt.execute(sql);

        }
        return hasResultSet;
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        printColumnNames(rsmd);
        // 打印ResultSet里的全部这段的值
        while (rs.next()) {
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                print(rs.getString(i + 1) + "\t");
            }
            print("\n");
        }
        rs.close();
    }
}
