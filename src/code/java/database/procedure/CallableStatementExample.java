package code.java.database.procedure;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class CallableStatementExample {
    public static void main(String[] args) {
        try {
            // 打开连接
            System.out.println("连接数据库...");
            Connection conn = FKJJYUtils.getSelectTestDBConnection();
            // 调用存储过程
            System.out.println("调用存储过程...");
            CallableStatement stmt = conn.prepareCall("{call my_test(?,?)}");
            stmt.setInt(1, 1); // 设置输入参数
            stmt.registerOutParameter(2, Types.INTEGER); // 注册输出参数
            boolean result = stmt.execute(); // 执行存储过程

            // 获取输出参数
            int count = stmt.getInt(2);
            System.out.println("存储过程返回值: " + result);
            System.out.println("输出参数: " + count);

            // 关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

