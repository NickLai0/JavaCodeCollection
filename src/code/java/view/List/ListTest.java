package code.java.view.List;

import code.java.utils.FrameUtils;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
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
public class ListTest extends JFrame {

    String[] books = new String[]{
            "疯狂Java讲义"
            , "轻量级Java EE企业应用实战"
            , "疯狂Android讲义"
            , "疯狂Ajax讲义"
            , "经典Java EE企业应用实战"
    };

    Vector<String> bookVector = new Vector<>();

    {
        for (String book : books) {
            bookVector.add(book);
        }
    }

    // 用一个字符串数组来创建一个JList对象
    JList<String> bookList = new JList<>(books);

    JComboBox<String> comboBox;

    // 定义布局选择按钮所在的面板
    JPanel layoutPanel = new JPanel();

    // 定义选择模式按钮所在的面板
    JPanel selectModePanel = new JPanel();

    JTextArea textArea = new JTextArea(4, 40);

    ButtonGroup buttonGroup = new ButtonGroup();
    ButtonGroup selectModeButtonGroup = new ButtonGroup();

    public ListTest() {
        setupCenter();
        setupSouth();
        pack();
        setupListener();
    }

    private void setupListener() {
        bookList.addListSelectionListener(new ListSelectionHandler());
        comboBox.addItemListener(new SelectBookItemHandler());
    }


    private void setupCenter() {
        addLayoutButton("纵向滚动", JList.VERTICAL);
        addLayoutButton("纵向换行", JList.VERTICAL_WRAP);
        addLayoutButton("横向换行", JList.HORIZONTAL_WRAP);
        // 设置JList的可视高度可同时显示三个列表项
        bookList.setVisibleRowCount(3);
        // 默认选中第三项到第五项（第一项的索引是0）
        bookList.setSelectionInterval(2, 4);

        addSelectModelButton("无限制", ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addSelectModelButton("单选", ListSelectionModel.SINGLE_SELECTION);
        addSelectModelButton("单范围", ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Box verticalBox = new Box(BoxLayout.Y_AXIS);
        // 将JList组件放在JScrollPane中，再将该JScrollPane添加到listBox容器中
        verticalBox.add(new JScrollPane(bookList));
        // 添加布局选择按钮面板、选择模式按钮面板
        verticalBox.add(layoutPanel);
        verticalBox.add(selectModePanel);

        // 用一个Vector对象来创建一个JComboBox对象
        comboBox = new JComboBox<>(bookVector);
        // 设置可以直接编辑
        comboBox.setEditable(true);
        // 设置下拉列表框的可视高度可同时显示4个列表项
        comboBox.setMaximumRowCount(4);
        JPanel p = new JPanel();
        p.add(comboBox);

        Box horizontalBox = new Box(BoxLayout.X_AXIS);
        horizontalBox.add(verticalBox);
        horizontalBox.add(p);
        add(horizontalBox, BorderLayout.CENTER);
    }

    private void setupSouth() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JScrollPane(textArea));
        p.add(new JLabel("您喜欢的图书："), BorderLayout.NORTH);
        add(p, BorderLayout.SOUTH);
    }

    private void addLayoutButton(String label, final int orientation) {
        layoutPanel.setBorder(new TitledBorder(new EtchedBorder(), "确定选项布局"));
        JRadioButton button = new JRadioButton(label);
        // 把该单选按钮添加到layoutPanel面板中
        layoutPanel.add(button);
        // 默认选中第一个按钮
        if (buttonGroup.getButtonCount() == 0) {
            button.setSelected(true);
        }
        buttonGroup.add(button);
        // 改变列表框里列表项的布局方向
        button.addActionListener(e -> bookList.setLayoutOrientation(orientation));
    }

    private void addSelectModelButton(String label, final int selectModel) {
        selectModePanel.setBorder(new TitledBorder(new EtchedBorder(), "确定选择模式"));
        JRadioButton button = new JRadioButton(label);
        // 把该单选按钮添加到selectModePanel面板中
        selectModePanel.add(button);
        // 默认选中第一个按钮
        if (selectModeButtonGroup.getButtonCount() == 0)
            button.setSelected(true);
        selectModeButtonGroup.add(button);
        button.addActionListener(e -> bookList.setSelectionMode(selectModel));
    }

    class ListSelectionHandler implements ListSelectionListener {  // ①
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // 获取用户所选择的所有图书
            List<String> books = bookList.getSelectedValuesList();
            textArea.setText("");
            for (String b : books) {
                textArea.append(b + "\n");
            }
        }
    }

    class SelectBookItemHandler implements ItemListener {  // ②
        @Override
        public void itemStateChanged(ItemEvent e) {
            // 获取JComboBox所选中的项
            Object book = comboBox.getSelectedItem();
            textArea.setText(book.toString());
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ListTest.class)
                .setTitle("测试列表框");
    }

}
