package code.java.database.stament.prepared;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static code.java.utils.LU.println;

/**
 * Modify a lot.
 *
 * 演示可滚动和可更新的ResultSet
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
public class ScrollableAndUpdatableResultSet {

    public void query(String sql) throws Exception {
        try (
                Connection conn = FKJJYUtils.getTestDBConnection();
                // 使用Connection来创建一个PreparedStatement对象
                // 传入控制结果集可滚动，可更新的参数。
                PreparedStatement pstmt = conn.prepareStatement(sql
                        , ResultSet.TYPE_SCROLL_INSENSITIVE
                        , ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pstmt.executeQuery()) {
            rs.last();
            int rowCount = rs.getRow();

            JDBCUtils.printColumnNames(rs);
            //第0行应该是列名，所以不打印。
            for (int i = rowCount; i > 0; i--) {
                rs.absolute(i);
                println(rs.getString(1) + "\t"
                        + rs.getString(2) + "\t"
                        + rs.getString(3));
                // 修改记录指针所有记录、第2列的值
                rs.updateString(2, "学生名" + i);
                // 提交修改
                rs.updateRow();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ScrollableAndUpdatableResultSet rt = new ScrollableAndUpdatableResultSet();
        rt.query("select * from student_table");
    }
}
