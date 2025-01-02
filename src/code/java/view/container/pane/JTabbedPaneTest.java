package code.java.view.container.pane;

import code.java.view.container.panel.ButtonPanel;
import code.java.view.event.action.ChangeAction;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
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
public class JTabbedPaneTest {
    JFrame jf = new JFrame("测试Tab页面");
    // 创建一个Tab页面的标签放在左边，采用换行布局策略的JTabbedPane
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT
            , JTabbedPane.WRAP_TAB_LAYOUT);
    ImageIcon icon = new ImageIcon("ico/close.gif");
    String[] layouts = {"换行布局", "滚动条布局"};
    String[] positions = {"左边", "顶部", "右边", "底部"};
    Map<String, String> books = new LinkedHashMap<>();

    public void init() {
        books.put("疯狂Java讲义", "fkjjyCover.png");
        books.put("轻量级Java EE企业应用实战", "ee.png");
        books.put("疯狂Ajax讲义", "ajax.png");
        books.put("疯狂Android讲义", "android.png");
        books.put("经典Java EE企业应用实战", "classic.png");
        String tip = "可看到本书的封面照片";
        // 向JTabbedPane中添加5个标签页面，指定了标题、图标和提示
        // 但该标签页面的组件为null
        for (String bookName : books.keySet()) {
            tabbedPane.addTab(bookName, icon, null, tip);
        }
        jf.add(tabbedPane, BorderLayout.CENTER);
        // 为JTabbedPane添加事件监听器
        tabbedPane.addChangeListener(event -> {
            // 如果被选择的组件依然是空
            if (tabbedPane.getSelectedComponent() == null) {
                // 获取所选标签页
                int n = tabbedPane.getSelectedIndex();
                // 为指定标前页加载内容
                loadTab(n);
            }
        });
        // 系统默认选择第一页，加载第一页内容
        loadTab(0);
        tabbedPane.setPreferredSize(new Dimension(500, 300));
        // 增加控制标签布局、标签位置的单选按钮
        JPanel buttonPanel = new JPanel();
        ChangeAction action = new ChangeAction(tabbedPane, layouts, positions);
        buttonPanel.add(new ButtonPanel(action, "选择标签布局策略", layouts));
        buttonPanel.add(new ButtonPanel(action, "选择标签位置", positions));
        jf.add(buttonPanel, BorderLayout.SOUTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

    // 为指定标签页加载内容
    private void loadTab(int n) {
        String title = tabbedPane.getTitleAt(n);
        // 根据标签页的标题获取对应图书封面
        ImageIcon bookImage = new ImageIcon("ico/"
                + books.get(title));
        tabbedPane.setComponentAt(n, new JLabel(bookImage));
        // 改变标签页的图标
        tabbedPane.setIconAt(n, new ImageIcon("ico/open.gif"));
    }


    public static void main(String[] args) {
        new JTabbedPaneTest().init();
    }
}

