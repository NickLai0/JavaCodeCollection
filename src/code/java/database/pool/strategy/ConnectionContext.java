package code.java.database.pool.strategy;

import java.sql.Connection;

//连接池上下文对象
public class ConnectionContext {

    private ConnectionPoolStrategy strategy;

    public ConnectionContext() {
        this(null);
    }

    public ConnectionContext(ConnectionPoolStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ConnectionPoolStrategy strategy) {
        this.strategy = strategy;
    }

    public Connection getConnection() {
        return strategy.getConnection();
    }

}
