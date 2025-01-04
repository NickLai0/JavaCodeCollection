package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.utils.ProjectFileUtils;
import code.java.view.table.tableModel.ResultSetTableModel;

import java.sql.*;
import java.io.*;
import java.util.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * todo:xxx因为还没弄懂jdbc，所以这个后续再看
 *
 * 数据来源JDBC数据库的TableModel案例。
 * 弄明白JDBC后
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
public class TableModelDataFromJDBCTest extends JFrame {

    private ResultSetTableModel model;

    private JScrollPane scrollPane;
    // 用于装载数据表的JComboBox
    private JComboBox<String> cbTableNames = new JComboBox<>();

    private JTextArea changeMsg = new JTextArea(4, 80);

    private ResultSet rs;

    private Connection conn;

    private Statement stmt;

    public TableModelDataFromJDBCTest() {
        // 为JComboBox添加事件监听器，当用户选择某个数据表时，触发该方法
        setupView();
        setupDatabase();
        setupListener();
    }

    private void setupView() {
        JPanel p = new JPanel();
        p.add(cbTableNames);
        add(p, BorderLayout.NORTH);
        add(new JScrollPane(changeMsg), BorderLayout.SOUTH);
        pack();
    }

    private void setupListener() {
        cbTableNames.addActionListener(new CheckNameAction());
        addWindowListener(new WindowClosingAdapter());
    }

    class WindowClosingAdapter extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupDatabase() {
        try {
            // 获取数据库连接
            conn = getConnection();
            // 获取数据库的MetaData对象
            DatabaseMetaData meta = conn.getMetaData();
            // 创建Statement
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE
                    , ResultSet.CONCUR_UPDATABLE
            );

            // 查询当前数据库的全部数据表
            ResultSet resultSet = meta.getTables(
                    null,
                    null,
                    null
                    , new String[]{"TABLE"}
            );

            // 将全部数据表添加到JComboBox中
            while (resultSet.next()) {
                cbTableNames.addItem(resultSet.getString(3));
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        // 通过加载conn.ini文件来获取数据库连接的详细信息
        Properties props = loadDatabaseProperties();
        String drivers = props.getProperty("jdbc.drivers");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        // 加载数据库驱动
        Class.forName(drivers);
        // 取得数据库连接
        return DriverManager.getConnection(url, username, password);
    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(
                ProjectFileUtils
                        .getsDatabaseDir()
                        .getAbsolutePath() + "\\fkjjy\\sql\\conn.ini"
        );
        props.load(in);
        in.close();
        return props;
    }

    class CheckNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                // 如果装载JTable的JScrollPane不为空
                if (scrollPane != null) {
                    // 从主窗口中删除表格
                    remove(scrollPane);
                }
                // 从JComboBox中取出用户试图管理的数据表的表名
                String tableName = (String) cbTableNames.getSelectedItem();
                // 如果结果集不为空，则关闭结果集
                if (rs != null) {
                    rs.close();
                }
                String query = "select * from " + tableName;
                // 查询用户选择的数据表
                rs = stmt.executeQuery(query);
                // 使用查询到的ResultSet创建TableModel对象
                model = new ResultSetTableModel(rs);
                // 为TableModel添加监听器，监听用户的修改
                model.addTableModelListener(evt -> {
                    int row = evt.getFirstRow();
                    int column = evt.getColumn();
                    changeMsg.append("修改的列:" + column
                            + ",修改的行:" + row + "修改后的值:"
                            + model.getValueAt(row, column));
                });
                // 使用TableModel创建JTable，并将对应表格添加到窗口中
                JTable table = new JTable(model);
                scrollPane = new JScrollPane(table);
                add(scrollPane, BorderLayout.CENTER);
                validate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(TableModelDataFromJDBCTest.class);
    }

}

