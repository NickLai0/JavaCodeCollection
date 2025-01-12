package code.java.database.dml;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.util.*;
import java.io.*;
import java.sql.*;

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
public class InsertBySelectingAnotherTable {
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

    public int insertData(String sql) throws Exception {
        // 加载驱动
        Class.forName(driver);
        try (
                // 获取数据库连接
                Connection conn = DriverManager.getConnection(url, user, pass);
                // 使用Connection来创建一个Statment对象
                Statement stmt = conn.createStatement()
        ) {
            // 执行DML,返回受影响的记录条数
            return stmt.executeUpdate(sql);
        }
    }

    public static void main(String[] args) throws Exception {
        InsertBySelectingAnotherTable ed = new InsertBySelectingAnotherTable();
        ed.initParam(FKJJYUtils.getInitFilePath());
        /*
         查询出student_table和teacher_table表的
         student_name和teacher_name列的数据，
         然后插入jdbc_test表的jdbc_name和jdbc_desc列，
         条件是student_table的java_teacher字段是
         teacher_table的teacher_id。
         */
        int result = ed.insertData("insert into jdbc_test(jdbc_name,jdbc_desc)"
                + "select s.student_name , t.teacher_name "
                + "from student_table s , teacher_table t "
                + "where s.java_teacher = t.teacher_id;");
        System.out.println("--系统中共有" + result + "条记录受影响--");
    }
}
