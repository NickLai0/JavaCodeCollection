package code.java.database.pool.strategy;

import code.java.database.pool.bean.ConnectionPoolConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseC3P0Strategy implements ConnectionPoolStrategy {
    BaseC3P0Strategy() {
        init();
    }

    //Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/logging/log4j/spi/LoggerContext
    ComboPooledDataSource cpds = new ComboPooledDataSource();

    private void init() {
        ConnectionPoolConfiguration c = getConfiguration();
        try {
            cpds.setDriverClass(c.getDriverClassName());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpds.setJdbcUrl(c.getUrl());
        cpds.setUser(c.getUsername());
        cpds.setPassword(c.getPassword());

        // Optional Settings
        //cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(c.getMinIdle());
        //cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(c.getMaxIdle());
        cpds.setMaxStatements(c.getMaxOpenPreparedStatements());
    }

    protected abstract @NotNull ConnectionPoolConfiguration getConfiguration();

    @Override
    public Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
