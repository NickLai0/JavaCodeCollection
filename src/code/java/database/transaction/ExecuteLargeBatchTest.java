package code.java.database.transaction;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.Connection;
import java.sql.Statement;

/**
 * 演示当个事务批量执行sql案例。
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
public class ExecuteLargeBatchTest {

    public static void insertBatch(String[] sqls) throws Exception {
        try (Connection conn = FKJJYUtils.getTestDBConnection()) {
            // 保存当前的自动的提交模式
            boolean autoCommit = conn.getAutoCommit();
            // 关闭自动提交
            conn.setAutoCommit(false);
            try ( // 使用Connection来创建一个Statement对象
                    Statement stmt = conn.createStatement()) {
                // 循环多次执行SQL语句
                for (String sql : sqls) {
                    stmt.addBatch(sql);
                }
                // 同时提交所有的SQL语句
                stmt.executeLargeBatch();
                // 提交修改
                conn.commit();
                // 恢复原有的自动提交模式
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String[] sqls = new String[]{
                "insert into student_table values(null , 'aaa' ,1)",
                "insert into student_table values(null , 'bbb' ,1)",
                "insert into student_table values(null , 'ccc' ,1)",
        };
        insertBatch(sqls);
    }

}
