package code.java.view.List.jList;

import code.java.utils.FrameUtils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
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
public class DefaultListModelTest extends JFrame {

    // 定义一个DefaultListModel对象
    private DefaultListModel<String> defaultListModel = new DefaultListModel<>();

    private JList<String> bookList;

    {
        // 向bookModel中添加元素
        defaultListModel.addElement("疯狂Java讲义");
        defaultListModel.addElement("轻量级Java EE企业应用实战");
        defaultListModel.addElement("疯狂Android讲义");
        defaultListModel.addElement("疯狂Ajax讲义");
        defaultListModel.addElement("经典Java EE企业应用实战");

        // 根据DefaultListModel对象创建一个JList对象
        bookList = new JList<>(defaultListModel);
        // 设置最大可视高度
        bookList.setVisibleRowCount(4);
        // 只能单选
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private JTextField bookName = new JTextField(20);
    private JButton removeBn = new JButton("删除选中图书");
    private JButton addBn = new JButton("添加指定图书");

    public  DefaultListModelTest() {
        setupView();
        setupListener();
    }

    private void setupView() {
        // 添加bookList组件
        add(new JScrollPane(bookList), BorderLayout.CENTER);

        // 将p面板添加到窗口中
        JPanel p = new JPanel();
        p.add(bookName);
        p.add(addBn);
        p.add(removeBn);
        add(p, BorderLayout.SOUTH);


        pack();
    }

    private void setupListener() {
        addBn.addActionListener(new AddAction());
        removeBn.addActionListener(new RemoveAction());
    }

    class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 当bookName文本框的内容不为空。
            if (!bookName.getText().trim().equals("")) {
                // 向bookModel中添加一个元素，
                // 系统自动会向JList中添加对应的列表项
                defaultListModel.addElement(bookName.getText());
            }
        }
    }

    class RemoveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果用户已经选中的一项
            if (bookList.getSelectedIndex() >= 0) {
                // 从bookModel中删除指定索引处的元素，
                // 系统自动会删除JList对应的列表项
                defaultListModel.removeElementAt(bookList.getSelectedIndex());
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(DefaultListModelTest.class)
                .setTitle("测试DefaultListModel");
    }
}
