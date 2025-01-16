package code.java.database.resultSet;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.*;
import javax.sql.rowset.*;

import static code.java.utils.LU.println;

/**
 * 《疯狂Java讲义》：JdbcRowSet、 CachedRowSet、 FilteredRowSet、 JoinRowSet和WebRowSet常用子接口。除JdbcRowSet需要保持与数据库的连接之外，其余4个子接口都是离线的RowSet，无须保持与数据库的连接。
 * 这里用到CachedRowSet离线接口来实现分页功能。
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
public class CachedRowSetPage {
    /**
     * 下面方法来自《疯狂Java讲义》，但是运行就报错
     * <p>
     * Exception in thread "main" java.sql.SQLException: Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY.
     * at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:121)
     * at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114)
     * at com.mysql.cj.jdbc.result.ResultSetImpl.absolute(ResultSetImpl.java:417)
     * at java.sql.rowset/com.sun.rowset.CachedRowSetImpl.populate(CachedRowSetImpl.java:7263)
     * at code.java.database.resultSet.CachedRowSetPage.query(CachedRowSetPage.java:39)
     * at code.java.database.resultSet.CachedRowSetPage.main(CachedRowSetPage.java:46)
     */
    public CachedRowSet query2(String sql, int pageSize, int page) throws Exception {
        try (  // 获取数据库连接
               Connection conn = FKJJYUtils.getTestDBConnection();
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(sql)) {
            // 使用RowSetProvider创建RowSetFactory
            RowSetFactory factory = RowSetProvider.newFactory();
            // 创建默认的CachedRowSet实例
            CachedRowSet cachedRs = factory.createCachedRowSet();
            // 设置每页显示pageSize条记录
            cachedRs.setPageSize(pageSize);
            // 使用ResultSet装填RowSet，设置从第几条记录开始
            cachedRs.populate(rs, (page - 1) * pageSize + 1);
            return cachedRs;
        }
    }

    public CachedRowSet query(String sql, int pageSize, int page) throws Exception {
        try (  // 获取数据库连接
               Connection conn = FKJJYUtils.getTestDBConnection();
               PreparedStatement pstmt = conn.prepareStatement(
                       sql
                       , ResultSet.TYPE_SCROLL_INSENSITIVE
                       , ResultSet.CONCUR_UPDATABLE
               );
               ResultSet rs = pstmt.executeQuery()
        ) {
            // 使用RowSetProvider创建RowSetFactory
            RowSetFactory factory = RowSetProvider.newFactory();
            // 创建默认的CachedRowSet实例
            CachedRowSet cachedRs = factory.createCachedRowSet();
            // 设置每页显示pageSize条记录
            cachedRs.setPageSize(pageSize);
            // 使用ResultSet装填RowSet，设置从第几条记录开始
            cachedRs.populate(rs, (page - 1) * pageSize + 1);
            return cachedRs;
        }
    }

    public static void main(String[] args) throws Exception {
        CachedRowSetPage cp = new CachedRowSetPage();
        CachedRowSet rs = cp.query("select * from student_table", 3, 2);   // ①

        // 向后滚动结果集
        while (rs.next()) {
            println(rs.getString(1)
                    + "\t" + rs.getString(2)
                    + "\t" + rs.getString(3)
            );
        }

    }

}