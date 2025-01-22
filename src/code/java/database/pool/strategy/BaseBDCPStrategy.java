package code.java.database.pool.strategy;

import code.java.database.pool.bean.ConnectionPoolConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
//策略模式的变种，实现接口后，还有个基础抽象类~
public abstract class BaseBDCPStrategy implements ConnectionPoolStrategy {

    private ConnectionPoolConfiguration c;

    private BasicDataSource ds = new BasicDataSource();

    public BaseBDCPStrategy() {
        init();
    }

    private void init() {
        try {
            c = getConfiguration();
            ds.setDriverClassName(c.getDriverClassName());
            ds.setUrl(c.getUrl());
            ds.setUsername(c.getUsername());
            ds.setPassword(c.getPassword());

            ds.setMinIdle(c.getMinIdle());
            ds.setMaxIdle(c.getMaxIdle());
            ds.setMaxOpenPreparedStatements(c.getMaxOpenPreparedStatements());
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