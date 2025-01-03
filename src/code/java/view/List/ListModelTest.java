package code.java.view.List;

import code.java.utils.FrameUtils;
import code.java.view.List.model.NumberComboBoxModel;
import code.java.view.List.model.NumberListModel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.*;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
public class ListModelTest extends JFrame {

    // 根据NumberListModel对象来创建一个JList对象
    private JList<BigDecimal> numScopeList
            = new JList<>(new NumberListModel(1, 21, 2));

    // 根据NumberComboBoxModel对象来创建JComboBox对象
    private JComboBox<BigDecimal> comboBox
            = new JComboBox<>(new NumberComboBoxModel(0.1, 1.2, 0.1));

    private JTextField textField = new JTextField(20);

    public ListModelTest() {
        setupCenter();
        setupSouth();
        pack();
        setupListener();
    }

    private void setupSouth() {
        JPanel p = new JPanel();
        p.add(new JLabel("您选择的值是："));
        p.add(textField);
        add(p, BorderLayout.SOUTH);
    }

    private void setupCenter() {
        //横向Box
        Box box = new Box(BoxLayout.X_AXIS);
        //横向box左边是List
        box.add(new JScrollPane(numScopeList));
        JPanel p = new JPanel();
        p.add(comboBox);
        //横向box右边是panel
        box.add(p);
        add(box, BorderLayout.CENTER);

        // JList的可视高度可同时显示4个列表项
        numScopeList.setVisibleRowCount(4);
        // 默认选中第三项到第五项（第一项的索引是0）
        numScopeList.setSelectionInterval(2, 4);
        // 设置每个列表项具有指定的高度和宽度。
        numScopeList.setFixedCellHeight(30);
        numScopeList.setFixedCellWidth(90);

        // 设置列表项的可视高度可显示5个列表项
        comboBox.setMaximumRowCount(5);
    }

    private void setupListener() {
        // 为numScopeList添加监听器
        numScopeList.addListSelectionListener(new ShowSelectedItemsHandler());
        // 为numScopeSelector添加监听器
        comboBox.addItemListener(new OnItemStateChangeHandler());
    }

    class OnItemStateChangeHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            // 获取JComboBox中选中的数字
            Object num = comboBox.getSelectedItem();
            textField.setText(num.toString());
        }
    }

    class ShowSelectedItemsHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // 获取用户所选中的所有数字
            List<BigDecimal> nums = numScopeList.getSelectedValuesList();
            textField.setText("");
            // 把用户选中的数字添加到单行文本框中
            for (BigDecimal num : nums) {
                textField.setText(textField.getText() + num.toString() + ", ");
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ListModelTest.class)
                .setTitle("Test ListModel and Box component");
    }
}

