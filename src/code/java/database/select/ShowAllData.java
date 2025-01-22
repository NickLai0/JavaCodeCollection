package code.java.database.select;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

//展示一张表的所有数据
public class ShowAllData {


    private String tableName;

    public ShowAllData(String tableName) throws Exception {
        this.tableName = tableName;
    }


    public void show() throws Exception {
        println("------查询" + tableName + "表的所有数据-----");
        executeSql("select * from " + tableName);
    }

    public void executeSql(String sql) throws Exception {
        try ( // 获取数据库连接
              Connection conn = FKJJYUtils.getSelectTestDBConnection();
              // 使用Connection来创建一个Statement对象
              Statement stmt = conn.createStatement()) {
            // 执行SQL,返回boolean值表示是否包含ResultSet
            boolean hasResultSet = stmt.execute(sql);
            // 如果执行后有ResultSet结果集
            if (hasResultSet) {
                try (ResultSet rs = stmt.getResultSet()/*获取结果集*/) {
                    parseResultSet(rs);
                }
            } else {
                println("该SQL语句影响的记录有" + stmt.getUpdateCount() + "条");
            }
        }
    }

    //遍历查询出来的结果，逐列逐行打印。
    private static void parseResultSet(ResultSet rs) throws SQLException {
        // ResultSetMetaData是用于分析结果集的元数据接口
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        // 迭代输出ResultSet对象
        while (rs.next()) {
            // 依次输出每列的值
            for (int i = 0; i < columnCount; i++) {
                print(rs.getString(i + 1) + "\t");
            }
            print("\n");
        }
    }

    public static void main(String[] args) throws Exception {
        new ShowAllData("my_test").show();
    }
}
