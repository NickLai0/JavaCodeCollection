package code.java.database.pool.strategy;

import code.java.database.pool.bean.ConnectionPoolConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
//策略模式的变种，实现接口后，还有个基础抽象类~
public abstract class BaseStrategy implements ConnectionPoolStrategy {

    private ConnectionPoolConfiguration c;

    private BasicDataSource ds = new BasicDataSource();

    public BaseStrategy() {
        init();
    }

    private void init() {
        try {
            c = getConfiguration();
            ds.setDriverClassName(c.getDriverClassName());
            ds.setUrl(c.getUrl());
            ds.setUsername(c.getUsername());
            ds.setPassword(c.getPassword());

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //子类提供具体配置对象
    protected abstract ConnectionPoolConfiguration getConfiguration();

    @Override
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}