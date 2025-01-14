package code.java.database.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 注：本案例的sql来源：项目目录\src\res\db\fkjjy\sql\select_data.sql
 * <p>
 * 要用到mysql的驱动库如：mysql-connector-j-9.0.0.jar
 * 按：《疯狂Java讲义》第五版用：mysql-connector-java8.0.13.jar
 * 按：《疯狂Java讲义》第四版用：mysql-connector-java-5.1.44-bin.jar
 * 按：这里的代码却是来自第四版,所以代码要对照第五版调整，要命。
 * <p>
 * modified.
 *
 * <p>
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
public class ConnectMySqlSimplified {
    public static void main(String[] args) throws Exception {

        // 1.加载驱动，使用反射的知识，现在记住这么写。
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (//实现了AutoCloseable接口的都可这样写，会自动关闭资源。
             // 2.使用DriverManager获取数据库连接,
             // 其中返回的Connection就代表了Java程序和数据库的连接
             // 不同数据库的URL写法需要查驱动文档知道，用户名、密码由DBA分配

             /**
              * select_test是数据库的名字，里面有student_table和teacher_table表
              *
              * allowPublicKeyRetrieval=true解决：Caused by: com. mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Public Key Retrieval is not allowed
              * javax.net.ssl.SSLException: closing inbound before receiving peer's close_notify
              *
              * useSSL=false解决：javax.net.ssl.SSLException: closing inbound before receiving peer's close_notify
              *
              */
             Connection conn = DriverManager.getConnection(
                     //localhost等价于127.0.0.1:3306
                     "jdbc:mysql://127.0.0.1:3306/select_test?allowPublicKeyRetrieval=true&useSSL=false&serverTimeZone=UTC"
                     , "test"//数据库账号（acount）
                     , "testtesttest"//数据库密码（password）
             );
             // 3.使用Connection来创建一个Statment对象
             Statement stmt = conn.createStatement();
             // 4.执行SQL语句
			/*
			Statement有三种执行sql语句的方法：
			1 execute 可执行任何SQL语句。- 返回一个boolean值，
			  如果执行后第一个结果是ResultSet，则返回true，否则返回false
			2 executeQuery 执行Select语句 － 返回查询到的结果集
			3 executeUpdate 用于执行DML语句。－ 返回一个整数，
			  代表被SQL语句影响的记录条数
			*/
             ResultSet rs = stmt.executeQuery(
                     "select s.* , teacher_name"
                             + " from student_table s , teacher_table t"
                             + " where t.teacher_id = s.java_teacher")
        ) {
            // ResultSet有系列的getXxx(列索引 | 列名)，用于获取记录指针
            // 指向行、特定列的值，不断地使用next()将记录指针下移一行，
            // 如果移动之后记录指针依然指向有效行，则next()方法返回true。
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t"
                        + rs.getString(2) + "\t"
                        + rs.getString(3) + "\t"
                        + rs.getString(4));
            }
        }
    }
}
