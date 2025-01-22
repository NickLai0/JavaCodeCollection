package code.java.database.pool.utils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import code.java.data.JDBCDriverInfo;
import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import static code.java.utils.LU.println;

public class C3P0DatabaseUtility {

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException, SQLException, IOException, ClassNotFoundException {
        JDBCDriverInfo di = FKJJYUtils.getJDBCDriverInfo();
//Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/logging/log4j/spi/LoggerContext
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(di.getDriverClassName());
        cpds.setJdbcUrl(di.getUrl());
        cpds.setUser(di.getUsername());
        cpds.setPassword(di.getPassword());

        // Optional Settings
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            ComboPooledDataSource dataSource = C3P0DatabaseUtility.getDataSource();
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM student_table");

            println("The Connection Object is of Class: " + connection.getClass());

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                println(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
            }

        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }
    }
}
