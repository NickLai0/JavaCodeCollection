package code.java.database.pool.strategy;

import java.sql.Connection;

//抽象是接口
public interface ConnectionPoolStrategy {
    //有返回值
    Connection getConnection();
}