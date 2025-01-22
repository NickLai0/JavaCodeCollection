package code.java.database.pool.strategy;

import code.java.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static code.java.utils.LU.printSeparateLine;

public class DBCPConnectionPoolTest {
    public static void main(String[] args) throws SQLException {
        ConnectionContext context = new ConnectionContext(new DBCP4FKJJYDBStrategy());

        Connection conn = context.getConnection();
        Statement statement = conn.createStatement();
        try (ResultSet rs = statement.executeQuery("select * from student_table")) {
            printSeparateLine();
            JDBCUtils.printResultSet(rs);
        }

        context.setStrategy(new DBCP4SystemSchemaDBStrategy());
        try (ResultSet rs = statement.executeQuery("select * from teacher_table")) {
            printSeparateLine();
            JDBCUtils.printResultSet(rs);
        }
    }
}
