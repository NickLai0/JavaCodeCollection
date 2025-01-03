package code.java.view.List;

import code.java.utils.FrameUtils;
import code.java.view.List.renderer.ImageListCellRenderer;

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
public class ListRenderingTest extends JFrame {

    private String[] friends = new String[]{
            "李清照",
            "苏格拉底",
            "李白",
            "弄玉",
            "虎头"
    };
    // 定义一个JList对象
    private JList<String> friendsList = new JList<>(friends);

    public ListRenderingTest() {
        // 设置该JList使用ImageCellRenderer作为列表项绘制器
        friendsList.setCellRenderer(new ImageListCellRenderer());
        add(new JScrollPane(friendsList));
        pack();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ListRenderingTest.class)
                .setTitle("好友列表");
    }

}


