package code.java.database.pool.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import code.java.data.JDBCDriverInfo;
import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import org.apache.commons.dbcp2.BasicDataSource;

//演示数据库连接池的使用
public class DataBaseUtility {

    private static BasicDataSource dataSource;

    private static BasicDataSource getDataSource() throws SQLException, IOException, ClassNotFoundException {

        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            JDBCDriverInfo di = FKJJYUtils.getJDBCDriverInfo();

            ds.setDriverClassName(di.getDriverClassName());
            ds.setUrl(di.getUrl());
            ds.setUsername(di.getUsername());
            ds.setPassword(di.getPassword());

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            dataSource = ds;
        }
        return dataSource;
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        try (BasicDataSource dataSource = DataBaseUtility.getDataSource();
             Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM student_table");) {
            System.out.println("The Connection Object is of Class: " + connection.getClass());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
                }
            }
        }
    }

}
