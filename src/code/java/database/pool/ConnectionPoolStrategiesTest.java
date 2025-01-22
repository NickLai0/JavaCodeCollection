package code.java.database.pool;

import code.java.database.pool.strategy.C3P04FKJJYDBStrategy;
import code.java.database.pool.strategy.ConnectionContext;
import code.java.database.pool.strategy.DBCP4FKJJYDBStrategy;
import code.java.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static code.java.utils.LU.printSeparateLine;

public class ConnectionPoolStrategiesTest {
    public static void main(String[] args) throws SQLException {
        //用DBCP的数据库连接池策略。
        ConnectionContext context = new ConnectionContext(new DBCP4FKJJYDBStrategy());
        Connection conn = context.getConnection();
        Statement statement = conn.createStatement();
        try (ResultSet rs = statement.executeQuery("select * from student_table")) {
            printSeparateLine();
            JDBCUtils.printResultSet(rs);
        }

        //用 C3P0的数据库连接池策略。
        context.setStrategy(new C3P04FKJJYDBStrategy());
        conn = context.getConnection();
        statement = conn.createStatement();
        try (ResultSet rs = statement.executeQuery("select * from teacher_table")) {
            printSeparateLine();
            JDBCUtils.printResultSet(rs);
        }

        /*

        下面案例因为 system_information数据库里没有teacher_table，所以报错

        context.setStrategy(new DBCP4SystemSchemaDBStrategy());
        conn = context.getConnection();
        statement = conn.createStatement();
        try (ResultSet rs = statement.executeQuery("select * from teacher_table")) {
            printSeparateLine();
            JDBCUtils.printResultSet(rs);
        }*/


    }
}
