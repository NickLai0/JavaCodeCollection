package code.java.database.select.query;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;
import code.java.view.container.dialog.JDialog.HintDialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

/**
 * 可自己编写sql来查询数据库表的案例
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
public class QueryExecutor extends JFrame {

    private static Connection conn;
    private static Statement stmt;

    // 采用静态初始化块来初始化Connection、Statement对象
    static {
        try {
            // 取得数据库连接
            conn = FKJJYUtils.getSelectTestDBConnection();
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JScrollPane scrollPane;

    private JButton execBn = new JButton("查询");

    // 用于输入查询语句的文本框
    private JTextField sqlField = new JTextField(45);

    // --------初始化界面的方法---------
    public void init() {
        setupView();
        setupListener();
    }

    private void setupListener() {
        // 为执行按钮、单行文本框添加事件监听器
        execBn.addActionListener(new L());
        sqlField.addActionListener(new L());
    }

    private void setupView() {
        setSize(680, 480);
        JPanel top = new JPanel();
        top.add(new JLabel("输入查询语句："));
        top.add(sqlField);
        top.add(execBn);
        add(top, BorderLayout.NORTH);
    }

    // 定义监听器
    class L implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (scrollPane != null) {
                //remove old JTable component.
                remove(scrollPane);
            }
            try (ResultSet rs = stmt.executeQuery(sqlField.getText())) {
                // 取出ResultSet的MetaData
                ResultSetMetaData rsmd = rs.getMetaData();
                Vector<String> columnNames = new Vector<>();
                // 把ResultSet的所有列名添加到Vector里
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnNames.add(rsmd.getColumnName(i + 1));
                }
                Vector<Vector<String>> data = new Vector<>();
                // 把ResultSet的所有记录添加到Vector里
                while (rs.next()) {
                    Vector<String> v = new Vector<>();
                    for (int i = 0, colCount = rsmd.getColumnCount(); i < colCount; i++) {
                        v.add(rs.getString(i + 1));
                    }
                    data.add(v);
                }
                add(scrollPane = new JScrollPane(new JTable(data, columnNames)));
                // 更新主窗口
                validate();
            } catch (Exception e) {
                new HintDialog(QueryExecutor.this, e.toString()).setVisible(true);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(QueryExecutor.class, true).setTitle("查询执行器");
    }

}
