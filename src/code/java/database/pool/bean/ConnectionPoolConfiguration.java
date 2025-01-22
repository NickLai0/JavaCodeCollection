package code.java.database.pool.bean;

import code.java.data.JDBCDriverInfo;

import java.io.IOException;

public class ConnectionPoolConfiguration extends JDBCDriverInfo {

    private int minIdle;
    private int maxIdle;
    private int maxOpenPreparedStatements;

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public static ConnectionPoolConfiguration create4FKJJY() throws IOException {
        return create(JDBCDriverInfo.create4FKJJY());
    }

    public static ConnectionPoolConfiguration create4informationSchema() throws IOException {
        return create(JDBCDriverInfo.create4informationSchema());
    }

    private static ConnectionPoolConfiguration create(JDBCDriverInfo di) {
        ConnectionPoolConfiguration c = new ConnectionPoolConfiguration();

        c.driverClassName = di.getDriverClassName();
        c.url = di.getUrl();
        c.username = di.getUsername();
        c.password = di.getPassword();

        c.maxIdle = 10;
        c.minIdle = 5;
        c.maxOpenPreparedStatements = 1000;

        return c;
    }

}
