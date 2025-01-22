package code.java.view.table.JTable;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;
import code.java.view.table.tableModel.ResultSetTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

import static code.java.utils.LU.println;

/**
 * Modify a lot.
 *
 * <p>
 * 数据来源JDBC数据库的TableModel案例。
 * 弄明白JDBC后
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
public class TablesDataFromDatabase extends JFrame {

    private ResultSetTableModel model;

    private JScrollPane curScrollPane;
    // 用于装载数据表的JComboBox
    private JComboBox<String> cbTableNameList = new JComboBox<>();

    private JTextArea changeMsg = new JTextArea(4, 80);

    private ResultSet rs;

    private Connection conn;

    private Statement stmt;

    public TablesDataFromDatabase() {
        setupView();
        setupDatabase();
        setupListener();
    }

    private void setupView() {
        setSize(400, 600);
        // 为JComboBox添加事件监听器，当用户选择某个数据表时，触发该方法
        setupNorth();
        add(new JScrollPane(changeMsg), BorderLayout.SOUTH);
        //不让视图自适应，setSize才有效果
        //pack();
    }

    private void setupNorth() {
        JPanel p = new JPanel();
        p.add(cbTableNameList);
        add(p, BorderLayout.NORTH);
    }

    private void setupListener() {
        cbTableNameList.addActionListener(new CheckNameAction());
        addWindowListener(new ClosingConnectionAdapter());
    }

    class ClosingConnectionAdapter extends WindowAdapter {
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
            conn = FKJJYUtils.getSelectTestDBConnection();
            // 获取数据库的MetaData对象
            DatabaseMetaData meta = conn.getMetaData();
            // 创建Statement
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // 查询当前数据库的全部数据表
            ResultSet resultSet = meta.getTables(null, null, null, new String[]{"TABLE"});
            // 将全部数据表添加到JComboBox中
            while (resultSet.next()) {
                String tableName = resultSet.getString(3);
                cbTableNameList.addItem(tableName);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CheckNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                if (curScrollPane != null) {
                    //删除老scrollPane从主窗口中删除表格
                    remove(curScrollPane);
                }
                // 从JComboBox中取出用户试图管理的数据表的表名
                String tableName = (String) cbTableNameList.getSelectedItem();
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
                model.addTableModelListener(new ShowModifiedCellDataHandler());
                // 使用TableModel创建JTable，并将对应表格添加到窗口中
                JTable table = new JTable(model);
                add(curScrollPane = new JScrollPane(table), BorderLayout.CENTER);
                validate();//通知重新绘制Component
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    class ShowModifiedCellDataHandler implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            changeMsg.append("修改的列:" + column);
            changeMsg.append("，修改的行:" + row);
            changeMsg.append("，修改后的值:" + model.getValueAt(row, column));
            changeMsg.append("\n");
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(
                TablesDataFromDatabase.class
        );
    }

}

