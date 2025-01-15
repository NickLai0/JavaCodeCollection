package code.java.database.ddl;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.JDBCUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static code.java.utils.LU.println;

/**
 * 创建带有blob数据类型的img_table。
 */
public class CreateImgTableIfNotExists {
    public static void main(String[] args) throws Exception {
        String createImgTableSql = "create table if not exists img_table "
                + "(img_id int auto_increment primary key, "
                + "img_name varchar(255), "
                + "img_data blob);";
        Connection c = FKJJYUtils.getTestDBConnection();
        JDBCUtils.executeSql(c, createImgTableSql);
    }
}

