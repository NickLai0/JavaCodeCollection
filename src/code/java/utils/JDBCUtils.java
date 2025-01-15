package code.java.utils;

import code.java.database.select.ShowAllData;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
    public static void printColumnNames(ResultSetMetaData rsmd, String separator) throws SQLException {
        for (int i = 0, size = rsmd.getColumnCount(); i < size; i++) {
            print(rsmd.getColumnName(i + 1));
            print(separator);
        }
        println();
    }


}
