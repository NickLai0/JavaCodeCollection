package code.java.database.resultSet;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.*;
import javax.sql.rowset.*;

/**
 *演示查询出数据后，转为离线数据（CachedRowSet）然后更新离线数据，再将离线数据同步到数据库
 * Modify a lot.
 *
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class CachedRowSetTest {

    public CachedRowSet queryCachedData(String sql) throws Exception {
        // 获取数据库连接
        Connection conn = FKJJYUtils.getTestDBConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        // 使用RowSetProvider创建RowSetFactory
        RowSetFactory factory = RowSetProvider.newFactory();
        // 创建默认的CachedRowSet实例
        CachedRowSet cachedRs = factory.createCachedRowSet();
        // 使用ResultSet装填RowSet
        cachedRs.populate(rs);    // ①
        // 关闭资源
        rs.close();
        stmt.close();
        conn.close();
        return cachedRs;
    }

    public static void main(String[] args) throws Exception {
        CachedRowSetTest ct = new CachedRowSetTest();
        CachedRowSet rs = ct.queryCachedData("select * from student_table");
        rs.afterLast();
        // 向前滚动结果集
        while (rs.previous()) {
            System.out.println(rs.getString(1)
                    + "\t" + rs.getString(2)
                    + "\t" + rs.getString(3));
            if (rs.getInt("student_id") == 3) {
                // 修改指定记录行
                rs.updateString("student_name", "孙悟空");
                rs.updateRow();
            }
        }
        // 重新获取数据库连接
        Connection conn = FKJJYUtils.getTestDBConnection();
        /*
            If a connection is
            in auto-commit mode, then all its SQL
            statements will be executed and committed
            as individual transactions.

            Otherwise,
            its SQL statements are grouped into
            transactions that are terminated by a
            call to either the method commit or the
            method rollback. By default, new connections
            are in auto-commit mode.
        */
        conn.setAutoCommit(false);
        // 把对RowSet所做的修改同步到底层数据库
        rs.acceptChanges(conn);
    }

}

