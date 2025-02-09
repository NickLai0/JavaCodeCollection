package code.java.database.ddl;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.util.*;
import java.io.*;
import java.sql.*;

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
public class CreateTableByExecuteUpdateMethod {
    public static void main(String[] args) throws Exception {
        CreateTableByExecuteUpdateMethod ed = new CreateTableByExecuteUpdateMethod();
        ed.initParam(FKJJYUtils.getInitFileTestSelect());
        ed.createTable("create table jdbc_test "
                + "( jdbc_id int auto_increment primary key, "
                + "jdbc_name varchar(255), "
                + "jdbc_desc text);");
        System.out.println("-----建表成功-----");
    }

    private String driver;
    private String url;
    private String user;
    private String pass;

    public void initParam(String paramFile) throws Exception {
        println("paramFile=" + paramFile);
        // 使用Properties类来加载属性文件
        Properties props = new Properties();
        props.load(new FileInputStream(paramFile));

        driver = props.getProperty("jdbc.drivers");
        url = props.getProperty("jdbc.url");
        user = props.getProperty("jdbc.username");
        pass = props.getProperty("jdbc.password");

        println(toString());
    }

    public void createTable(String sql) throws Exception {
        // 加载驱动
        Class.forName(driver);
        try (
                // 获取数据库连接
                Connection conn = DriverManager.getConnection(url, user, pass);
                // 使用Connection来创建一个Statment对象
                Statement stmt = conn.createStatement()) {
            // 执行DDL,创建数据表
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public String toString() {
        return "ExecuteDDL{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}

