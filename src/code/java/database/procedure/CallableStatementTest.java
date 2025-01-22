package code.java.database.procedure;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.*;

/**
 * 演示用CallableStatement来执行数据库系统所定义的加法求值存储过程。
 *
 * Modify a lot.
 * <p>
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
public class CallableStatementTest {

    public void callProcedure() throws Exception {
        try (
                // 获取数据库连接
                Connection conn = FKJJYUtils.getSelectTestDBConnection();
                // 使用Connection来创建一个CallableStatment对象
                CallableStatement cstmt = conn.prepareCall("{call AddNumbers(?,?,?)}")) {
            cstmt.setInt(1, 4);
            cstmt.setInt(2, 5);
            // 注册CallableStatement的第三个参数是int类型
            cstmt.registerOutParameter(3, Types.INTEGER);
            // 执行存储过程
            cstmt.execute();
            // 获取，并输出存储过程传出参数的值。
            System.out.println("执行结果是: " + cstmt.getInt(3));
        }
    }

    public static void main(String[] args) throws Exception {
        CallableStatementTest ct = new CallableStatementTest();
        ct.callProcedure();
    }

}

