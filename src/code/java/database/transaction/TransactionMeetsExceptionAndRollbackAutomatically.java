package code.java.database.transaction;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.*;

/**
 * 演示：JDBC开启事务，执行四条DML语句，最后一条刻意出错，导致事务自动回滚。
 *
 * <p>
 * Modify a lot.
 *
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
public class TransactionMeetsExceptionAndRollbackAutomatically {

    public void insertInTransaction(String[] sqls) throws Exception {
        try (Connection conn = FKJJYUtils.getTestDBConnection()) {
            // 关闭自动提交，开启事务
            conn.setAutoCommit(false);
            // 使用Connection来创建一个Statment对象
            try (Statement stmt = conn.createStatement()) {
                // 循环多次执行SQL语句
                for (String sql : sqls) {
                    stmt.executeUpdate(sql);
                }
            }
            /**
             Makes all changes made since the previous commit/
             rollback permanent and releases any database
             locks currently held by this Connection object.
             This method should be used only when auto-commit
             mode has been disabled.
             */
            // 提交事务——所有sql都成功才全部成功，一条失败即全部失败。
            conn.commit();
        }
    }

    public static void main(String[] args) throws Exception {
        TransactionMeetsExceptionAndRollbackAutomatically tt = new TransactionMeetsExceptionAndRollbackAutomatically();
        try {
            String[] sqls = new String[]{
                    "insert into student_table values(null , 'aaa' ,1)",
                    "insert into student_table values(null , 'bbb' ,1)",
                    "insert into student_table values(null , 'ccc' ,1)",
                    //
                    /*
                     * create table teacher_table
                     * (
                     * 	# auto_increment:实际上代表所有数据库的自动编号策略，通常用作数据表的逻辑主键。
                     * 	teacher_id int auto_increment,
                     * 	teacher_name varchar(255),
                     * 	primary key(teacher_id)
                     * );
                     *
                     * create table student_table
                     * (
                     * 	# 为本表建立主键约束
                     * 	student_id int auto_increment primary key,
                     * 	student_name varchar(255),
                     * 	# 指定java_teacher参照到teacher_table的teacher_id列
                     * 	java_teacher int,
                     * 	foreign key(java_teacher) references teacher_table(teacher_id)
                     * );
                     *
                     * 下面这条SQL语句将会违反foreign key约束，
                     * 因为teacher_table中没有ID为5的记录。
                    */
                    "insert into student_table values(null , 'ccc' ,5)" //①
            };

            /*
                当事务所包含的任意一个数据库操作执行失败后，
                应该回滚 （rollback）事务，使该事务中所做的修改全部失效。
                事务回滚有两 种方式：显式回滚和自动回滚。
                    ➢ 显式回滚：使用rollback。
                    ➢ 自动回滚：系统错误或者强行退出。

                按：这里显然是执行过程中，系统错误，所以自动回滚。
             */
            tt.insertInTransaction(sqls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

