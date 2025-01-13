package code.java.database;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.util.*;
import java.io.*;
import java.sql.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

/**
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class ExecuteSQL {
    private String driver;
    private String url;
    private String user;
    private String pass;

    public void initParam(String paramFile) throws Exception {
        // 使用Properties类来加载属性文件
        Properties props = new Properties();
        props.load(new FileInputStream(paramFile));
        driver = props.getProperty("jdbc.drivers");
        url = props.getProperty("jdbc.url");
        user = props.getProperty("jdbc.username");
        pass = props.getProperty("jdbc.password");
    }

    public void executeSql(String sql) throws Exception {
        // 加载驱动
        Class.forName(driver);
        try ( // 获取数据库连接
              Connection conn = DriverManager.getConnection(url, user, pass);
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
        ExecuteSQL es = new ExecuteSQL();
        es.initParam(FKJJYUtils.getInitFilePath());

        println("------执行删除表的DDL语句-----");
        es.executeSql("drop table if exists my_test");

        println("------执行建表的DDL语句-----");
        es.executeSql("create table my_test"
                + "(test_id int auto_increment primary key, "
                + "test_name varchar(255))");

        println("------执行插入数据的DML语句-----");
        es.executeSql("insert into my_test(test_name) "
                + "select student_name from student_table");

        println("------执行查询数据的查询语句-----");
        es.executeSql("select * from my_test");
    }
}
