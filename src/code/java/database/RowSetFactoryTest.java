package code.java.database;

import code.java.data.JDBCDriverInfo;
import code.java.io.file.book.fkjjy.utils.FKJJYUtils;

import javax.sql.rowset.*;

import java.sql.SQLException;

import static code.java.utils.LU.println;

/**
 *
 * Modify a lot。
 *
 * 提示：实际运行该程序会提示“No suitable driver”，出现这个错误应该是由于MySQL驱动对JdbcRowSet支持得不太好。
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
public class RowSetFactoryTest {

    //创建RowSetFactory->创建JdbcRowSet-》配置信息-》执行sql-》返回JdbcRowSet结果
    public JdbcRowSet execute(String sql) throws Exception {
        JDBCDriverInfo di = FKJJYUtils.getJDBCDriverInfo();
        //加载驱动
        Class.forName(di.getDriverClassName());

        //使用RowSetProvider创建RowSetFactory
        RowSetFactory factory = RowSetProvider.newFactory();

        //使用RowSetFactory创建默认的JdbcRowSet实例
        JdbcRowSet rowSet = factory.createJdbcRowSet();
        //设置必要的连接信息
        rowSet.setUrl(di.getUrl());
        rowSet.setUsername(di.getUsername());
        rowSet.setPassword(di.getPassword());

        rowSet.setCommand(sql); //设置SQL查询语句
        rowSet.execute();// 执行查询

        return rowSet;
    }

    private void update(JdbcRowSet rowSet) throws SQLException {
        //Moves the cursor to the end of this ResultSet object
        rowSet.afterLast();
        // Moves the cursor to the previous row in this ResultSet object.
        while (rowSet.previous()) {
            println(rowSet.getString(1) + "\t" + rowSet.getString(2) + "\t" + rowSet.getString(3));
            if (rowSet.getInt("student_id") == 3) {
                // 修改指定记录行
                rowSet.updateString("student_name", "孙悟空");
                rowSet.updateRow();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        RowSetFactoryTest jt = new RowSetFactoryTest();
        String sql = "select * from student_table";
        JdbcRowSet rowSet = jt.execute(sql);
        jt.update(rowSet);
        rowSet.close();
    }

}

