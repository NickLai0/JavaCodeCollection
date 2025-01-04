package code.java.view.List;

import code.java.utils.FrameUtils;
import code.java.view.List.renderer.ImageListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ListRenderingTest extends JFrame {

    private String[] friendArr = new String[]{
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头",
    };

    // 定义一个JList对象
    private JList<String> friendList = new JList<>(friendArr);

    JButton button = new JButton("调用JList.updateUI()，观察更新日志情况");

    public ListRenderingTest() {
        setupView();
        button.addActionListener(new UpdateUIAction());
    }

    private void setupView() {
        // 设置该JList使用ImageCellRenderer作为列表项绘制器
        friendList.setCellRenderer(new ImageListCellRenderer());
//        add(friendList, BorderLayout.CENTER);
        //要加JScrollPane才能滚动，所以上面代码不妥。
        add(new JScrollPane(friendList), BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        pack();
    }

    class UpdateUIAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            friendList.updateUI();
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ListRenderingTest.class)
                .setTitle("好友列表");
    }

}


