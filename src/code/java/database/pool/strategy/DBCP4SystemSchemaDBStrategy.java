package code.java.database.pool.strategy;

import code.java.database.pool.bean.ConnectionPoolConfiguration;

import java.io.IOException;

//疯狂Java讲义数据库连接池DBCP策略
public class DBCP4SystemSchemaDBStrategy extends BaseStrategy {

    @Override
    protected ConnectionPoolConfiguration getConfiguration() {
        try {
            return ConnectionPoolConfiguration.create4informationSchema();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
