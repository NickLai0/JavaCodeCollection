package code.java.database;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import static code.java.utils.JDBCUtils.printResultSet;
import static code.java.utils.LU.println;

/**
 * modify a lot。
 * <p>
 * 演示显示DatabaseMetaData的信息：
 * 1、MySQL支持的表类型信息
 * 2、当前数据库里的数据表信息                              -
 * 3、student_table表的主键信息
 * 4、当前数据库里的存储过程信息                             -
 * 5、teacher_table表和student_table之间 的外键约束
 * 6、student_table表的全部数据列
 * <p>
 * <p>
 * <p>
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
public class PrintDatabaseMetaDataInfo {

    public static void main(String[] args) throws Exception {
        try (Connection conn = FKJJYUtils.getTestDBConnection()) {
            // 获取的DatabaseMetaData对象
            DatabaseMetaData dbmd = conn.getMetaData();

            println("------------MySQL支持的表类型信息----------------");
            printResultSet(dbmd.getTableTypes());

            // 获取当前数据库的全部数据表
            ResultSet rs = dbmd.getTables(null, null, "%", new String[]{"TABLE"});
            println("------------当前数据库里的数据表信息----------------");
            printResultSet(rs);

            // 获取student_table表的主键
            rs = dbmd.getPrimaryKeys(null, null, "student_table");
            println("------------student_table表的主键信息----------------");
            printResultSet(rs);
            // 获取当前数据库的全部存储过程
            rs = dbmd.getProcedures(null, null, "%");

            println("------------当前数据库里的存储过程信息----------------");
            printResultSet(rs);

            // 获取teacher_table表和student_table之间的外键约束
            rs = dbmd.getCrossReference(
                    null,
                    null,
                    "teacher_table",
                    null,
                    null,
                    "student_table"
            );
            println("------------teacher_table表和student_table之间的外键约束----------------");
            printResultSet(rs);

            // 获取student_table表的全部数据列
            rs = dbmd.getColumns(null, null, "student_table", "%");
            println("------------student_table表的全部数据列----------------");
            printResultSet(rs);
        }

    }

}
